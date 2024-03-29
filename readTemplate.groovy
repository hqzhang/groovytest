#!/usr/bin/env groovy
 import groovy.yaml.YamlSlurper

def addProperty(fileName, directoryName, propName, propValue) {
    println("enter addProperties=$fileName")
    
    def xml = new XmlSlurper().parse(fileName)
    xml.category.each {
        if (it.@name==directoryName ) {
          println it.@name
          it.children().findAll { it.@name == propName }.replaceNode {}
          it.appendNode {  property(name: propName, value: propValue)  }
        }
    }
    println groovy.xml.XmlUtil.serialize(xml)
    def writer = new FileWriter(fileName)
    XmlUtil.serialize(xml, writer)
}

def readHabsRecipes(recipeFile){
    File fh = new File(recipeFile)
    def lines = fh.readLines()
    def result = new LinkedHashMap([:])
    def prekey=''
    def key=''
    def val=''
    def flag=1
    def i=1
    lines.each{ line->
    if ( line.contains("[") && line.contains("]") ) {
        key=line.replace('[','').replace(']','')
        if( prekey != '' ) {
            def tmp=[:]
            tmp.sect=prekey
            tmp.cont=val
            result.put(i,tmp)
            prekey=key
            val=''
            i++
        }
    } else if ( line != '' ){ 
         val = val+line+"\n"
    }
    if (flag) {
         prekey=key 
         flag = 1
    }
 }
 if ( prekey != '' ) {
    def tmp=[:]
    tmp.sect=prekey 
    tmp.cont=val
    prekey=key
    result.put(i,tmp)
    val=''
    i++
 }
 return result
}

def dynamicStages( result ){
 result.each { mykey, myval->
   println("sect=${myval.sect}")
   switch(myval.sect) { 
      case 'DB PATHES': 
          println "case DB PATHES" 
          break
      case 'CONFIG UPDATE': 
          println "case CONFIG UPDATE"
          def fileName= myval.cont.split("\n")[0]
          def content= myval.cont.split().drop(1).join("\n")
          def xml = new XmlSlurper().parseText(content)
          def dirName=xml.@name
          def propName=xml.property[0].@name
          def propValue=xml.property[0].@value
          println fileName
          println dirName
          println propName
          addProperty(fileName,dirName,propName,propValue)     
          break
      case 'HABS': 
          println "case HABS"
          break
      case 'SENS':
          println "case CONFIGUPDATEB"
          break
      case 'REPORTs':
          println "case CONFIGUPDATEB"
          break
      case 'SOA': 
          println "case soa"
          break
      default:
          println "case Default" 
          break
    }  
 }
}

def result=readHabsRecipes("habs_recipes")
dynamicStages(result)



