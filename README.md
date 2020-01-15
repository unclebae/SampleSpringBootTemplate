# file upload 

ref: https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/

ref: https://mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/

## Single File Upload

```
curl --location --request POST 'http://localhost:8080/uploadFile?file=' \
--header 'Content-Type: multipart/form-data; boundary=--------------------------836554004342212166925997' \
--form 'file=@/Users/kidobae/Desktop/retrospective.png'
```

## Multiple Files Upload

```$xslt
curl --location --request POST 'http://localhost:8080/uploadMultipleFiles' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--form 'files=@/Users/kidobae/Desktop/uxstd-redux-router01.png' \
--form 'files=@/Users/kidobae/Desktop/retrospective.png' \
--form 'files=@/Users/kidobae/Desktop/uxstd-redux-router02.png'
```

## get file download

```$xslt
curl --location --request GET 'http://localhost:8080/downloadFile/uxstd-redux-router01.png'
```