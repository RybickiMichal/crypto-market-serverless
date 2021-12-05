# Prerequisites
- Graalvm = 19.3.1
- [Serverless framework >= 1.56.1](https://serverless.com/framework/docs/getting-started/)
- AWS account

# Build Jvm & Native
`mvn clean package & mvn package -Dnative-lambda=true`

It will take some time....

# Run it
1. Deploy to AWS
`sls deploy -v`

2. Find out your API endoints
`sls info`
   
3. Don't forget to cleanup your AWS account if not using
`sls remove`