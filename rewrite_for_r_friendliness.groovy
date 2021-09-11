def osentout = [ ['iri', 'VeryPositive', 'Positive', 'Neutral', 'Negative', 'VeryNegative'] ] 
new File('./ontology_sentiments.tsv').splitEachLine('\t') {
  if(it[1] == 'NO_DEFINITION') { return; } 
  def newOut = []
  newOut << "http://purl.obolibrary.org/obo/" + it[0].replace('.txt', '')
  newOut << it[3].tokenize(':')[1]
  newOut << it[4].tokenize(':')[1]
  newOut << it[5].tokenize(':')[1]
  newOut << it[6].tokenize(':')[1]
  newOut << it[7].tokenize(':')[1]

  osentout << newOut
}

def msentout = [ ['iri', 'VeryPositive', 'Positive', 'Neutral', 'Negative', 'VeryNegative'] ] 
new File('./mimic_sentiments.tsv').splitEachLine('\t') {
  def newOut = []
  newOut << it[0]
  it[1].tokenize(',').each { v ->
    newOut << v.tokenize(':')[1] 
  }

  msentout << newOut
}

//osentout = osentout.findAll { msentout.find { ii -> it[0] == ii[0] } }
//msentout = msentout.findAll { osentout.find { ii -> it[0] == ii[0] } }
osentout = osentout.collect { it.join('\t') }
msentout = msentout.collect { it.join('\t') }

new File('r_mimic_sentiments.tsv').text = msentout.join('\n')
new File('r_ontology_sentiments.tsv').text = osentout.join('\n')
