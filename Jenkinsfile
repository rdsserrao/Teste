pipeline {
    agent {
            label "mvn"
    }
        parameters {
            string(name: 'Imagem', defaultValue: 'movies', description: 'Nome da imagem')
            string(name: 'Contentor', defaultValue: 'mov', description: 'Nome do Contentor')
            string(name: 'Porta', defaultValue: '3000', description: 'Número da Porta')
        }
            stages {
                stage('Clone') {
                    steps {
                        git branch: 'main', url: 'https://github.com/rdsserrao/Teste.git'
                    }
                }
                stage('SonarQube analysis') {
                    steps {
                            withSonarQubeEnv('sonarqube') {
                                    sh "mvn clean package sonar:sonar \
                                    -D sonar.login=f5f103028120cd31b483291025b64a8a640aa10c \
                                    -D sonar.projectKey=Teste \
                                    -D sonar.host.url=http://sonar:9000/"
                            }
                        }
                    }
                stage("Quality Gate") {
                    steps {
			            script {
                            timeout(time: 1, unit: 'HOURS') {
					            def qualitygate = waitForQualityGate()
					            if (qualitygate.status != "OK") {
					            error "Pipeline aborted due to quality gate coverage failure: ${qualitygate.status}"
					            }
                            }
                        }
                    }
                }
                stage ('Criar Imagem') {
                    steps {
                        sh 'docker rmi -f $Imagem'
                        sh 'docker build -t $Imagem .'
                    }   
                }
                stage ('Criar Contentor') {
                    steps {
                        sh 'docker rm -f $Contentor'
                        sh 'docker run -p $Porta:8080 -d --name $Contentor $Imagem'
                    }   
                }
                stage ('Enviar para o Nexus') {
                    steps {
                        withCredentials([usernamePassword(credentialsId: 'nexus', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh 'docker login -u $USER -p $PASS localhost:8082'
                    }
                        sh 'docker tag $Imagem localhost:8082/$Imagem:1.0'
                        sh 'docker push localhost:8082/$Imagem:1.0'
                    }   
                }
                stage ('Criar artefato no raw') {
                    steps {
                        withCredentials([usernamePassword(credentialsId: 'nexus', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                        sh 'curl -v --user "$USER":"$PASS" --upload-file ./target/*.jar http://nexus:8081/repository/raw_repo/'
                        }
                    }   
                }
                stage('Clean') {
                    steps {
                        cleanWs()
                    }
                }
            }
}