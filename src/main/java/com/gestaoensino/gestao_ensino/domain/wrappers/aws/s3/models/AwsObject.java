package com.gestaoensino.gestao_ensino.domain.wrappers.aws.s3.models;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.Date;

public class AwsObject {

    private String bucket;
    private String key;
    private Date ultimaModificacao;

    public AwsObject() {
    }

    public AwsObject(S3ObjectSummary s3ObjectSummary) {
        bucket = s3ObjectSummary.getBucketName();
        key = s3ObjectSummary.getKey();
        ultimaModificacao = s3ObjectSummary.getLastModified();
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getUltimaModificacao() {
        return ultimaModificacao;
    }

    public void setUltimaModificacao(Date ultimaModificacao) {
        this.ultimaModificacao = ultimaModificacao;
    }
}


//{
//        "bucketName": "dev-brasilseg-motor",
//        "key": "Outro",
//        "size": 7,
//        "lastModified": "2019-08-20T19:05:38.000+0000",
//        "storageClass": "STANDARD",
//        "owner": null,
//        "etag": "9e67c17ea2ea29ef3bd9b5031897829c"
//        },