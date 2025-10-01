pipeline {
    agent any

    // =======================================
    // =         환경 변수 (Environment)       =
    // =======================================
    environment {
        // AWS ECR 리포지토리 URL (Jenkins Credential 또는 환경 변수로 설정)
        ECR_REPOSITORY_URL = '${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/babsim-backend'
        // AWS 리전
        AWS_REGION = 'ap-northeast-2'
        // Docker 이미지 이름
        IMAGE_NAME = 'babsim-backend'
    }

    // =======================================
    // =          파이프라인 단계 (Stages)       =
    // =======================================
    stages {
        // 1. 소스 코드 체크아웃
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-repo/babsim-backend.git' // TODO: 실제 Git 리포지토리 URL로 변경
            }
        }

        // 2. Gradle 빌드 및 테스트
        stage('Build & Test') {
            steps {
                script {
                    // 실행 권한 부여
                    sh 'chmod +x ./gradlew'
                    // Gradle 빌드 및 테스트 실행
                    sh './gradlew clean build'
                }
            }
        }

        // 3. Docker 이미지 빌드
        stage('Build Docker Image') {
            steps {
                script {
                    // Docker 이미지 빌드 (버전은 빌드 번호 사용)
                    def dockerImage = docker.build("${IMAGE_NAME}:${BUILD_NUMBER}", ".")
                }
            }
        }

        // 4. AWS ECR에 이미지 푸시
        stage('Push to ECR') {
            steps {
                script {
                    // AWS ECR 로그인 및 이미지 푸시
                    // Jenkins에 AWS Credentials가 'aws-credentials' ID로 등록되어 있어야 함
                    docker.withRegistry("https://${ECR_REPOSITORY_URL}", 'ecr:ap-northeast-2:aws-credentials') {
                        sh "docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${ECR_REPOSITORY_URL}:${BUILD_NUMBER}"
                        sh "docker push ${ECR_REPOSITORY_URL}:${BUILD_NUMBER}"
                        sh "docker push ${ECR_REPOSITORY_URL}:latest"
                    }
                }
            }
        }

        // 5. AWS EC2에 배포 (예시)
        stage('Deploy to EC2') {
            steps {
                echo 'Deploying to EC2...'
                // Jenkins에 SSH 접속 정보가 'ec2-ssh-key'로 등록되어 있어야 함
                // withCredentials([sshUserPrivateKey(credentialsId: 'ec2-ssh-key', keyFileVariable: 'KEY_FILE')]) {
                //     sh '''
                //         ssh -o StrictHostKeyChecking=no -i $KEY_FILE ubuntu@${EC2_INSTANCE_IP} \
                //         "docker pull ${ECR_REPOSITORY_URL}:${BUILD_NUMBER} && \ 
                //          docker stop babsim-app || true && \ 
                //          docker rm babsim-app || true && \ 
                //          docker run -d --name babsim-app -p 8080:8080 \ 
                //          -e DB_URL=${DB_URL} \ 
                //          -e DB_USERNAME=${DB_USERNAME} \ 
                //          -e DB_PASSWORD=${DB_PASSWORD} \ 
                //          -e REDIS_HOST=${REDIS_HOST} \ 
                //          ${ECR_REPOSITORY_URL}:${BUILD_NUMBER}"
                //     '''
                // }
            }
        }
    }

    // =======================================
    // =        파이프라인 종료 후 작업        =
    // =======================================
    post {
        always {
            echo 'Pipeline finished.'
            // 빌드 과정에서 생성된 중간 파일 정리
            cleanWs()
        }
        success {
            echo 'Pipeline succeeded!'
            // TODO: 성공 시 슬랙/이메일 알림
        }
        failure {
            echo 'Pipeline failed.'
            // TODO: 실패 시 슬랙/이메일 알림
        }
    }
}
