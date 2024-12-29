package com.isadora.client.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@Setter
public class FotoMultiPart implements MultipartFile {
    private Long id;
    private String nome;
    private String formato;
    private byte[] conteudo;

    public FotoMultiPart(String nome, String formato, byte[] conteudo) {
        this.nome = nome;
        this.formato = formato;
        this.conteudo = conteudo;
    }

    @Override
    public String getName() {
        return nome;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return formato;
    }

    @Override
    public boolean isEmpty() {
        return conteudo.length == 0;
    }

    @Override
    public long getSize() {
        return conteudo.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return conteudo;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(conteudo);
    }

    @Override
    public Resource getResource() {
        return new ByteArrayResource(conteudo);  // Use ByteArrayResource to avoid URL dependencies
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileUtils.writeByteArrayToFile(dest, conteudo);
    }

    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {
        Files.write(dest, conteudo);
    }
}
