hello world
def inputString = """Name:abc
Type:p11
Ver:123
Name:xyz
Type:p33
Ver:888"""

// Split the input string by newline character to get individual lines
def lines = inputString.split('\n')

// Initialize maps to store names and types with corresponding values
def nameTypeMap = [:]
def typeValueMap = [:]

// Iterate through each line
lines.each { line ->
    // Split each line by colon
    def parts = line.split(':')
    def key = parts[0].trim()
    def value = parts[1].trim()
    
    // Check if the key is "Name" or "Type"
    if (key == "Name") {
        // If key is "Name", store the name and type in the nameTypeMap
        nameTypeMap[value] = parts[2].trim()
    } else if (key == "Type") {
        // If key is "Type", store the type and ver in the typeValueMap
        def type = value
        def ver = lines[lines.indexOf(line) + 1].split(':')[1].trim()
        typeValueMap[type] = ver
    }
}

// Print the nameTypeMap and typeValueMap
println "[$nameTypeMap] and [$typeValueMap]"
ipeline{
    agent any
    stages{
        stage('init') {
            steps{
                script{
                    echo "params=$params"
                     withFileParameter('FILE') {
                    sh """
                        echo "upload:$FILE_FILENAME"
                        cp $FILE $FILE_FILENAME
                        scp -i ~/.ssh/id_rsa $FILE_FILENAME root@192.168.0.55:~/workspace
                        """
                    }
                }
            }
        }
    }
}

