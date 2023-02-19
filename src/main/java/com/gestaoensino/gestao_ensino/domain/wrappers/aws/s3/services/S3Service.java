package com.gestaoensino.gestao_ensino.domain.wrappers.aws.s3.services;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.gestaoensino.gestao_ensino.domain.wrappers.aws.exceptions.AmazonException;
import com.gestaoensino.gestao_ensino.domain.wrappers.aws.s3.models.AwsBucket;
import com.gestaoensino.gestao_ensino.domain.wrappers.aws.s3.models.AwsObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper para o uso da api da AWS S3
 * Contém todos os métodos necessários para: Listar Buckets, Listar objetos por bucket, Exibir detalhes por bucket, Enviar, Baixar e Excluir objetos.
 * Há duas maneiras de usar:
 * 1 - Passando no construtor somente a região da aws (ex: "sa-east-1") e passando sempre o nome do bucket nos métodos.
 * 2 - Passando no construtor a região da aws (ex: "sa-east-1") e o nome do bucket caso vá realizar as operações sempre no mesmo bucket, tornando desnecessário passar o nome do bucket nos métodos.
 * <p>
 * Consulte a URL https://docs.aws.amazon.com/pt_br/general/latest/gr/rande.html para saber as regiões disponíveis.
 */
public class S3Service {

    public static final String BAR_DELIMITE_STR = "/";
    private AmazonS3 s3Client;
    private Regions clientRegion;
    private String bucketName;

    public S3Service(String regiao) {
        initialize(regiao);
    }

    public S3Service(String regiao, String bucketName) {
        this.bucketName = bucketName;
        initialize(regiao);
    }

    private void initialize(String regiao) {
        setRegion(regiao);
        s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
    }

    private void setRegion(String regiao) {
        try {
            clientRegion = Regions.fromName(regiao);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Região inexistente. Consulte a URL https://docs.aws.amazon.com/pt_br/general/latest/gr/rande.html para saber as regiões disponíveis.");
        }
    }

    public List<AwsBucket> listarBuckets() {
        List<AwsBucket> buckets = new ArrayList<>();

        s3Client.listBuckets().forEach(bucket -> {
            AwsBucket awsBucket = new AwsBucket(bucket);
            buckets.add(awsBucket);
        });
        return buckets;
    }

    public List<AwsObject> listarArquivosPorBucket(String bucketName) {
        ListObjectsV2Result result = s3Client.listObjectsV2(bucketName);
        List<AwsObject> objects = new ArrayList<>();

        result.getObjectSummaries().forEach(s3ObjectSummary -> {
            AwsObject awsObject = new AwsObject(s3ObjectSummary);
            objects.add(awsObject);
        });
        return objects;
    }

    public List<AwsObject> listarArquivosPorBucket() {
        return listarArquivosPorBucket(this.bucketName);
    }

    /**
     * @param arquivo       Arquivo a ser enviado
     * @param objectKeyName nome que terá o arquivo dentro do bucket com a extensão (ex: Log.txt).
     *                      Caso precise colocar o arquivo dentro de uma pasta no bucket, separe o nome da pasta e do arquivo com uma "/" (ex: logs/Log.txt).
     */
    public void enviaArquivo(File arquivo, String objectKeyName) {
        s3Client.putObject(bucketName, objectKeyName, arquivo);
    }


    /**
     * @param arquivo       Arquivo a ser enviado
     * @param bucketName    Nome do bucket aonde será enviado o arquivo
     * @param objectKeyName nome que terá o arquivo dentro do bucket com a extensão (ex: Log.txt).
     *                      Caso precise colocar o arquivo dentro de uma pasta no bucket, separe o nome da pasta e do arquivo com uma "/" (ex: logs/Log.txt).
     */
    public void enviaArquivo(File arquivo, String objectKeyName, String bucketName) {
        s3Client.putObject(bucketName, objectKeyName, arquivo);
    }

    /**
     * @param keyName   Nome completo do arquivo a ser baixado com a pasta e extensão (ex: Log.txt ou logs/Log.txt)
     * @param localPath Caminho completo do local aonde será gravado o arquivo (ex: C:/logs)
     *                  Este método utiliza o atributo bucketName da classe para definir o bucket.
     */
    public void baixarArquivo(String keyName, String localPath) {
        baixarArquivo(bucketName, keyName, localPath);
    }

    /**
     * @param bucketName Nome do bucket de onde será baixado o arquivo
     * @param keyName    Nome completo do arquivo a ser baixado com a pasta e extensão (ex: Log.txt ou logs/Log.txt)
     * @param localPath  Caminho completo do local aonde será gravado o arquivo (ex: C:/logs)
     *                   Este método utiliza o atributo bucketName da classe para definir o bucket.
     */
    public void baixarArquivo(String bucketName, String keyName, String localPath) {
        try {
            S3Object o = s3Client.getObject(bucketName, keyName);
            S3ObjectInputStream s3is = o.getObjectContent();
            try (FileOutputStream fos = new FileOutputStream(new File(localPath + BAR_DELIMITE_STR
                    + keyName.replace(keyName.substring(0, keyName.indexOf(BAR_DELIMITE_STR) + 1), "")))) {
                byte[] readBuff = new byte[1024];
                int readLen;
                while ((readLen = s3is.read(readBuff)) > 0) {
                    fos.write(readBuff, 0, readLen);
                }
                s3is.close();
            }
        } catch (AmazonServiceException | IOException e) {
            throw new AmazonException(e.getMessage(), e);
        }
    }
    
    /**
     * Método para baixar um arquivo do S3 e retornar como objeto File.
     * 
     * @param bucketName Nome do bucket de onde será baixado o arquivo
     * @param keyName    Nome completo do arquivo a ser baixado com a pasta e extensão (ex: Log.txt ou logs/Log.txt)
     * @param localPath  Caminho completo do local aonde será gravado o arquivo (ex: C:/logs)
     *                   Este método utiliza o atributo bucketName da classe para definir o bucket.
     */
    public File baixarArquivoNaMemoria(String bucketName, String keyName, String localPath) {
    	File file = null;
    	try {
            S3Object o = s3Client.getObject(bucketName, keyName);
            S3ObjectInputStream s3is = o.getObjectContent();

            file = new File(localPath + BAR_DELIMITE_STR
                    + keyName.replace(keyName.substring(0, keyName.indexOf(BAR_DELIMITE_STR) + 1), ""));
            s3is.close();

        } catch (AmazonServiceException | IOException e) {
            throw new AmazonException(e.getMessage(), e);
        }
    	
    	return file;
    }

    public void excluirArquivo(String keyName) {
        excluirArquivo(bucketName, keyName);
    }

    public void excluirArquivo(String bucketName, String keyName) {
        try {
            s3Client.deleteObject(bucketName, keyName);
        } catch (AmazonServiceException e) {
            throw new AmazonException(e.getMessage(), e);
        }
    }

    public String getFileUrl(String filePath) {
        return s3Client.getUrl(bucketName, filePath).toExternalForm();
    }

    public AccessControlList getAccessControlList(String objectKey) {
        return s3Client.getObjectAcl(bucketName, objectKey);
    }

    public S3Object getObject(String objectKey) {
        return getObject(bucketName, objectKey);
    }

    public S3Object getObject(String bucketName, String objectKey) {
        return s3Client.getObject(bucketName, objectKey);
    }

    public AccessControlList updateObjectAccessControl(String objectKey, AccessControlList accessControlList) {
        s3Client.setObjectAcl(bucketName, objectKey, accessControlList);
        return getAccessControlList(objectKey);
    }
}
