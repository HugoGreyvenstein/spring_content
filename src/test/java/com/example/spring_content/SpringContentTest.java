package com.example.spring_content;

import com.example.spring_content.contentstore.SopDocumentContent;
import com.example.spring_content.entities.SopDocument;
import com.example.spring_content.repository.SopDocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringContentTest
{
    private static final String FILE_CONTENTS = "######################### content #########################";
    @Autowired
    SopDocumentContent contentStore;

    @Autowired
    SopDocumentRepository repository;

    private static File file = new File("/tmp/init.txt");

    private SopDocument document = new SopDocument();

    @BeforeClass
    public static void initFile() throws IOException
    {
        boolean b = file.createNewFile();
        Files.write(file.toPath(), FILE_CONTENTS.getBytes());
    }

    @Before
    public void persistDocument()
    {
        //        document = repository.save(document);
    }

    @After
    public void removeDocument()
    {
        //        repository.deleteAll();
    }

    @Test
    public void persist() throws Exception
    {
        Optional<SopDocument> byIdDocument = repository.findById(document.getId());
        Assert.assertNull(byIdDocument.get().getContentId());
        //        log.info("document-no-content={}", byIdDocument.get());
        //        log.info("document-no-content-id={}", byIdDocument.get().getContentId());

        contentStore.setContent(byIdDocument.get(), new FileInputStream(file));
        Assert.assertNotNull(byIdDocument.get().getContentId());
        //        log.info("document-with-content={}", byIdDocument.get());
        //        log.info("document-with-content-id={}", byIdDocument.get().getContentId());

        InputStream content = contentStore.getContent(byIdDocument.get());
        String actual = readFileContents(content, FILE_CONTENTS.length());
        Assert.assertEquals(FILE_CONTENTS, actual);
        //        log.info("stored-contents-before-saved={}", actual);

        SopDocument saved = repository.save(byIdDocument.get());
        //        log.info("saved-document={}", saved);

        byIdDocument = repository.findById(document.getId());
        //        log.info("byIdDocument={}", byIdDocument.get().toString());
        content = contentStore.getContent(byIdDocument.get());
        actual = readFileContents(content, FILE_CONTENTS.length());

        //        log.info("stored-contents-after-saved={}", actual);
        Assert.assertEquals(FILE_CONTENTS, actual);
    }

    @Test
    public void createDocument() throws IOException
    {
        log.info("starting-test");
        SopDocument document = new SopDocument();
        repository.save(document);

        SopDocument lockedDocument = repository.lock(document);
        log.info("locked-document={}", lockedDocument);

        File file = new File("/tmp/random.txt");
        Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());
        contentStore.setContent(document, new FileInputStream(file));
        repository.save(document);
        SopDocument unlockedDocument = repository.unlock(document);
        log.info("unlocked-document={}", unlockedDocument);

        InputStream contentStream = contentStore.getContent(document);

        byte[] out = new byte[128];
        contentStream.read(out);
        log.info("out={}", new String(out));
    }

    private String readFileContents(InputStream content, int length) throws IOException
    {
        byte[] contents;
        String actual;
        contents = new byte[length];
        content.read(contents);
        actual = new String(contents);
        return actual;
    }

    @Test
    public void findContent() throws Exception
    {
        Optional<SopDocument> byIdDocument = repository.findById(document.getId());
        SopDocument documentContentStream = byIdDocument.orElseThrow(Exception::new);
        log.info("sop-document={}", byIdDocument);
        InputStream content = contentStore.getContent(documentContentStream);
        byte[] contents = new byte[FILE_CONTENTS.length()];
        content.read(contents);
        String actual = new String(contents);
        log.info("content2={}", actual);
        Assert.assertEquals(FILE_CONTENTS, actual);
    }

    @Test
    public void getDocument() throws IOException
    {
        Optional<SopDocument> byIdDocument = repository.findById(3621966L);
        log.info("byIdDocument={}", byIdDocument.get().toString());
        InputStream content = contentStore.getContent(byIdDocument.get());
        log.info("stream={}", content);
        String actual = readFileContents(content, FILE_CONTENTS.length());
        log.info("content={}", actual);
    }

    @Test
    public void updateContent() throws IOException
    {
        File file = new File("/tmp/random.txt");
        Files.write(file.toPath(), UUID.randomUUID().toString().getBytes());

        Optional<SopDocument> byId = repository.findById(3621966L);
        contentStore.setContent(byId.get(), new FileInputStream(file));
    }
}
