package com.isadora.client;

import com.isadora.client.dto.FotoMultiPart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "ImagemApiClient", contextId = "ImagemApiClient", url = "https://localhost:8085/api/imagem")
@Validated
public interface ImagemClient {
    @PatchMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FotoMultiPart> atualizar(@PathVariable Long id, @RequestPart("foto") MultipartFile file);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FotoMultiPart> adicionar(@RequestPart("foto") MultipartFile file);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarFoto(@PathVariable Long id);
}
