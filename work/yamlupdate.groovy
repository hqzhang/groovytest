#!/usr/bin/env groovy
import groovy.yaml.YamlSlurper
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.DumperOptions
import groovy.json.JsonSlurper

println "Hello world"

def parseConfig(String fileName){
    println("Enter parseConfig() ")
    
    String fileConts = new File('config').text

    Yaml yaml = new Yaml()
    def Map  map = (Map) yaml.load(fileConts)
    map['components'].each { comp ->
       println (comp)
       comp.each{ k, v-> 
          if (v instanceof Map){
            v.each{ kk,vv->
               println(kk+':'+vv)
            }
          } else {
             println(k+':'+v)
          }
       }
      
    }
    return map
}
org.yaml.snakeyaml.DumperOptions

def saveFile(output,data){
  println("INPUT:"+data)
  DumperOptions options = new DumperOptions()
  options.setPrettyFlow(true)
  options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
  yaml = new Yaml(options)
  yaml.dump(data, new FileWriter(output)) 
  
}
def dictUpdate(Map var,Map comp){
    println('Enter dictUpdate() config:'+comp)
    comp.each { k,v->
      println('cp:'+k+' '+v)
      if (v instanceof Map){
          println("object update")
          v.each { kk, vv->
               println("kk,vv:"+kk+' '+vv)
               var[k].each{ x, y-> 
                    println("x,y:"+x+' '+y)
                    if (kk.contains(x) ){
                        println("set "+kk+ " as "+vv)
                        var[k][x]=vv
                    }
               }
          }
      } else {
         println("single update")
         var.each { x, y->
             println("x,k:"+x+' '+k)
             if(k.contains(x)){
                println("set "+k+" as "+v)
                var[x]=v
             }
         }

      }
    
    }
    
    /*comp.each { k,v->
      if (v instanceof Map){
         println("instance update")
         v.each { kk, vv->
              var[k].each{ x, y->











                  if (kk.contains(x) ){
                      println("set "+kk+ " as "+vv)
                      var[k][x]=vv
                  }
              }
          }
      } else {
        println("single update")
        var.each { x,y->
          println("x,k:"+x+k)
          if(k.contains(x)){
            println("set "+k+"as"+v)
            var[x]=v
          }
        }
      }
      
    }*/
    println("END:"+var)
    return var
}
import groovy.yaml.YamlBuilder
def updateConfiguration(String fileName,String output){
    println("Enter updateConfiguration() "+fileName)
    String fileConts = new File(fileName).text
    def myFile = new File(output)
    println ("-----------parsing-------")
    def myyaml=new YamlSlurper()
    data = myyaml.parseText(fileConts)
    println("data=$data")
    println ("-----------toYaml-------")
    def yaml = new YamlBuilder()
    yaml(data)
    myFile.write(yaml.toString())
    println yaml.toString()
   /* fileConts='''
    components:
    - !component
      name: component1
      argv: first
      settings:
        username: hongqi
        password: nopass

    '''
    //println ('fileConts'+fileConts)
    //println  yamlSlurper['components']
    
    println('inputYaml:'+data)
    data['components'].each { var ->
        println('Loop:'+var)
        //when compoent name is matching
        params['components'].each { comp ->
            println(comp)
            println(var)
            if (comp['name'] == var['name']) {
              println('Do update')
              dictUpdate(var,comp)
            }
        }
    }
    saveFile(output,data)*/
}

def readYamlFileExt(String fileName){
    String fileConts = new File(fileName).text
    println ("-----------parsing-------")
    def myyaml=new YamlSlurper()
    return  myyaml.parseText(fileConts)
}
def readYamlFile(String fileName){
    Yaml yaml = new Yaml()
    String fileConts = new File(fileName).text
    return  (Map) yaml.load(fileConts)
}
def writeYamlFile(output,data){
    println("INPUT:"+data)
    DumperOptions options = new DumperOptions()
    options.setPrettyFlow(true)
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
    yaml = new Yaml(options)
    yaml.dump(data, new FileWriter(output)) 
}

 //ef struct=readYamlFile('configuration.yml')
 //writeYamlFile('configuration_out.yml',struct)
 data=[name: "hong",age: "18"]

DumperOptions options = new DumperOptions()
    //options.setPrettyFlow(true)
    //options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)
    println new Yaml(options).dump(data) 

def getFile(String mypath,String type, String dft){
  println("enter test()")
  def dft1='myfile'
def out="ssh root@192.168.0.16 ls /root/workspace/myscripts/*.tar.gz".execute().text
out=out.readLines().collect{ it.split("/")[-1] }
out.eachWithIndex{ it, id-> 
if ( it.contains(dft1) ){ index=id } }
out.add(0, out.remove(index))
return out
}
def getFileScript(String mypath,String type, String dft){
  return """def dft1='myfile'
  |def out="ssh root@192.168.0.16 ls /root/workspace/myscripts/*.tar.gz".execute().text
  |out=out.readLines().collect{ it.split("/")[-1] }
  |out.eachWithIndex{ it, id-> 
  |if ( it.contains(dft1) ){ index=id } }
  |out.add(0, out.remove(index))
  |return out
  |""".stripMargin()
}
@groovy.transform.Field
def envList=['DEV','BAT']
@groovy.transform.Field
def serversList=['DEV': ['APP': ['s1','ss22','ss33'] ],
              'BAT': ['APP': ['s3','s55'] ] ]
@groovy.transform.Field
def defaultList=['ss', 's55', 's23']

String buildDefault(List out,String key){
   def tmp=[]
   out.eachWithIndex{ it,id-> 
   if(it.contains(key)){ tmp.add(0, '"'+it+'"') } else { tmp.add('"'+it+'"') } }
   return tmp
}         
String getServersScript(String refvar){
    def map=[:]
    envList.eachWithIndex{ it,index->
       map[it]=buildDefault(serversList[it]['APP'],defaultList[index])
    }
    return """def map=${map}
    |return map[${refvar}]
    |""".stripMargin()
}
def getFile(String wksp, String dft){
    println("enter getFile(), $wksp")
    def tmp=[]
    def out="ls ${wksp}/release  ".execute().text
    println "out=$out"
    out=out.readLines().collect{  it.split("\\.")[0] } 
     println "out=$out"
    out.each{ if( it.contains(dft) ){ tmp.add(0,it) } else { tmp.add(it) } }
    return tmp
}  
def wksp="/Users/hongqizhang/workspace/wavecloud/groovytest"  

def greet(String name,  String greeting="") {
    def flag=''
    if ( !greeting?.trim() ) { println " EMPTY"  } else { println "NOT EMPT"}
    if ( greeting == "")  { println " EMPTYYYYYY"  } else { println "NOT EMPTYYYYY"}
    return "$greeting, $name!"
}


println greet("Hongq","zhang")
// Calling the method with only one argument (uses default value for greeting)
println greet("Alice") // Output: Hello, Alice!

// Calling the method without any arguments (uses default values for both name and greeting)

