@Grab('org.yaml:snakeyaml:1.28')
import org.yaml.snakeyaml.Yaml

def input = '''components:
  - !component
    name: component1
    argv: first
    settings:
      username: hongqi
      password: nopass'''

def yaml = new Yaml()
def data = yaml.load(input)
print data
println data.components[0].name
println data.components[0].settings.username
