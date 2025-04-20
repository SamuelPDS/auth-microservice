package com.example.AuthService.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class GotenbergService {

    private final WebClient webClient;

    public GotenbergService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:3000")
                .build();
    }

    public byte[] converterDocxParaPdf(String caminhoArquivo) throws IOException {
        File docxFile = new File(caminhoArquivo);

        if (!docxFile.exists()) {
            throw new IllegalArgumentException("Arquivo não encontrado: " + caminhoArquivo);
        }

        return webClient.post()
                .uri("/forms/libreoffice/convert")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("files", new FileSystemResource(docxFile)))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    public void salvarPdf(String caminhoDocx, String destinoPdf) throws IOException, IOException {
        byte[] pdf = converterDocxParaPdf(caminhoDocx);
        Files.write(new File(destinoPdf).toPath(), pdf);
    }
}

