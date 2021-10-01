def mappings = [:]
new File('./do_crossrefs').eachFile { f ->
  f.splitEachLine(':') {
    if(it[0] == 'MESH') {
      mappings[f.getName().tokenize('.')[0]] = it[1]
    }
  } 
}

def sents = [:]
new File('./propagated_do_mesh_sentiment_averages.tsv').splitEachLine('\t') {
  sents[it[0]] = it
}

def newOut = [['DO_iri', 'DO_ontology', 'DO_label', 'DO_VeryPositive', 'DO_Positive', 'DO_Neutral', 'DO_Negative',  'DO_VeryNegative', 'DO_count', 'MESH_iri', 'MESH_ontology', 'MESH_label', 'MESH_VeryPositive', 'MESH_Positive', 'MESH_Neutral', 'MESH_Negative',  'MESH_VeryNegative', 'MESH_count'].join('\t')]
sents.each { k, v ->
  if(v[1] == 'MESH') { return; }
  ok = k.tokenize('/').last()
  if(mappings.containsKey(ok)) {
    def match = sents.find { kk, vv -> vv[0] == 'http://purl.bioontology.org/ontology/MESH/'+mappings[ok] }
    if(match) {
      newOut << v.join('\t') + '\t' + match.getValue().join('\t')
    }
  }
}

new File('matching_propagated_mesh_do_pairs.tsv').text = newOut.join('\n')

