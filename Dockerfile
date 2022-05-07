FROM public.ecr.aws/docker/library/openjdk:11-oracle
LABEL maintainer="auditmanagement.authorization"
ADD target/audit-management-authorization-0.0.1-SNAPSHOT.jar audit-management-authorization.jar
ENTRYPOINT ["java","-jar","audit-management-authorization.jar"]
EXPOSE 7000