new File('death_doid_sentiments_propagated.tsv').splitEachLine('\t') { 
  if(!(new File('./do_crossrefs/'+it[0].tokenize('/').last()+'.txt').text =~ 'OMIM')) {
    println 'no omim map for ' + it[0]
  }
}


