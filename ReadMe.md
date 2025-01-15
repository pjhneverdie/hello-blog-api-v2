# 개발 환경

## 프로덕션 환경
* spring boot + redis on compose (ec2에서 구동)
* mysql on compose (홈서버에서 구동, rds 사용 x)

## 개발 환경 세팅 가이드
### 1. 기능 개발 및 테스트
테스트 코드 돌릴 때 / compose-localhost.yml 띄우면 테스트용 db 생성되니까 이거 띄우고,
스프링 부트는 application-testcase.yml 프로필로 테스트하면 됨.

테스트 코드 돌리는 게 아니고 직접 로컬에서 띄워서 브라우저나 포스트맨으로 빠르게 테스트할 경우도 마찬가지,

compose-localhost.yml 띄우면 테스트용 db랑 로컬 개발용 db 둘다 생성되니까 이거 띄우고,
스프링 부트는 application-localhost.yml 프로필로 테스트하면 됨.

### 2. 기능 테스트 후 실전 테스트
프로덕션 환경이랑 똑같이 compose로 spring boot + redis를 묶어서 up,
mysql은 같이 묶지 말고 다른 compose로 up 해서 프로덕션 환경 재현.

1. 프로젝트 빌드
2. compose-localhost-mysql-build.yml up (mysql only)
3. compose-localhost-project-build.yml up (spring boot + redis)