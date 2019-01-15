# contabil

1) BAIXAR O PROJETO

2) MVN CLEAN INSTALL

3) CRIAR IMAGEM A PARTIR DO Dockerfile

docker build -t contabil-thiago .
(Para conferir: docker image ls)

4) SUBIR DUAS INSTÂNCIAS:

I.1) docker run --name contabil-service-1 -itd -p 12001:8090 contabil-thiago:latest
I.2) docker run --name contabil-service-2 -itd -p 12002:8090 contabil-thiago:latest
(Para conferir: docker container ls)

Exemplo chamada GET: http://10.0.75.1:12001/lancamentos-contabeis/_stats
