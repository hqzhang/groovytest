import groovy.yaml.YamlSlurper
import org.yaml.snakeyaml.Yaml
println('');
println "Hello world"
Names=[:]
Types=[:]
def parseConfig(String fileName){
    println("Enter parseConfig() ")
    String fileConts = new File(fileName).text
    Yaml yaml = new Yaml()
    def Map  map = (Map) yaml.load(fileConts)
    map['components'].each { comp ->
       
        
        Names[comp['name']]=comp['type']
        if( !Types[comp['type']] ){
                Types[comp['type']]=comp['versions']  
        }
    }
    return map
}
parseConfig("/Users/hongqizhang/workspace/groovytest/config.yml")
println "Names=$Names"
println "Types:=$Types"
Names.each{ k,v ->
   println( k+':'+v)
}
Types.each {k,v->
   println( k+':'+v)
}
def firstKey= Names.iterator().next().getKey()
println firstKey