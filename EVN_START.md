# Active MQ

download from http://activemq.apache.org/components/classic/download/ 

and start

```$xslt
./activemq start
```

# MongoDB

https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/

brew install and start like this

```$xslt
brew service start mongodb-community@4.2
```

# 중요:
CQRS 예제에서 우리는 MongoDB 가 골든소스로 이용하며, 엘라스틱서치나 메모리용 NoSQL 등을활용하여 검색관점의 데이터를 구현한다. 

- MongoDB: 골든소스, 실제 DB
- ES : Query 용 DB 