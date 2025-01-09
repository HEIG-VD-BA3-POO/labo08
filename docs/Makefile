MD_FILE = rapport.md
OUTPUT = rapport.pdf
ASSETS = assets/
PLANTUML = plantuml
PLANTUML_OPTS = -nbthread auto -o $(ASSETS)
LIMIT = PLANTUML_LIMIT_SIZE=8192

default:
	@make build-all

build-all: build-pdf build-uml

build-pdf:
	pandoc -o $(OUTPUT) $(MD_FILE)

watch-pdf:
	@(find $(ASSETS) -type f; echo $(MD_FILE)) | entr -cs 'make build-pdf'

build-uml:
	$(LIMIT) $(PLANTUML) $(PLANTUML_OPTS) -tsvg uml/uml.puml
	$(LIMIT) $(PLANTUML) -nbthread auto -tpng uml/uml.puml
	$(LIMIT) $(PLANTUML) $(PLANTUML_OPTS) -tsvg uml/uml-simple.puml

watch-uml:
	@find uml/ -name "*.puml" | entr -s 'make build-uml'
