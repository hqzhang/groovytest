
rawdata="""\
name:comp1
type:type1
name:comp2
type:type2
name:xyz
type:daemon
versions:qqq
type:type1
versions:ver1
 type:type2
 version:vers2
 """
data=[[name: "comp1",type: "type1"],[name: "comp2", type: "type2"]]
version=[ "name:xyz", "type:daemon", "versions:qqq", "type:type1", "versions:ver1", "type:type2","version:vers2"]
println "Hello"
def flag=false
def start=true
type=''
def lss=[]
def map=[:]
rawdata.eachLine {
    kv=it.tokenize(":")
    if (it.contains('name')){
        if ( !start ) {
           println "assign map"
           lss.add(map); map=[:]
           map[kv[0]]=kv[1]
        } else { start=false }
        map[kv[0]]=kv[1]
    } else if (it.contains('daemon')){ 
        flag=true; return true
    } else if ( flag && it.contains('type')){ 
        type=kv[1]; return true
    } else if (type != ''){
        lss.each { cmp->
            if (cmp['type']==type){
                cmp['version']=kv[1];type = ''
            }
        }
    } else { map[kv[0]]=kv[1]}
}
println "lss=$lss"