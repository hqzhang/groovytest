
String buildScript(List values){
    def ret=values.collect { '"'+it+'"' }
    return "return ${ret}"
}

String convertScript( String str){
    def ret='"""'+str.replaceAll('"', '\\\\"')+'"""'
    return  "return $ret"
}

def getFileContent(String SolutionDetail ){
    def mf ="ls /Users/hongqizhang/workspace/ansibletest/releases  ".execute().text
    def myls = mf.readLines().collect{ it.split()[0].minus('.xml')}
    def map=[:]
    myls.each { file->
            my_tag="curl -k https://raw.githubusercontent.com/hqzhang/ansibletest/main/releases/${file}.xml".execute().text 
            map[ file]= my_tag
    }
    my_tag=map[SolutionDetail]
    def ret= "<textarea name=\"value\"  value  class=\"setting-input  \" type=\"text\">${my_tag}</textarea>"
    convertScript(ret)
}

def getFileList(){
    def test=''
    def mf ="ls /Users/hongqizhang/workspace/ansibletest/releases  ".execute().text
    def out=mf.readLines().collect{ it.split()[0].minus('.xml')}
    return out
}