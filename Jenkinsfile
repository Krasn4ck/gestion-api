//Agregamos Jenkins como orquestador para comprobar los despligues de la app
pipeline {
    agent {
        docker {
            // Jenkins usa este contenedor limpio con Java y Maven para compilar y probar
            image 'maven:3.9-eclipse-temurin-21'
            // Opcional: si lo necesitamos para pasar argumentos al contenedor
            args '-u root'
        }
    }

    stages {
        stage('Verificar Versión') {
            steps {
                // Comprobamos que el entorno sea correcto dentro del contenedor
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Compilar y Ejecutar Pruebas') {
            steps {
                // Si usamos Maven Wrapper (mvnw) en tu proyecto, puedes usar: ./mvnw clean test
                // Si usamos el Maven del contenedor:
                sh 'mvn clean test'
            }
        }

        stage('Empaquetar Aplicación (JAR)') {
            steps {
                // Genera el archivo .jar de tu Spring Boot sin volver a correr los tests
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        success {
            echo '¡Éxito! El código compiló perfectamente y todas las pruebas pasaron.'
        }
        failure {
            echo '¡Error! Alguna prueba falló o hubo un problema en la compilación.'
        }
    }
}