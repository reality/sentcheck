def sentiments = [:]
new File(args[0]).splitEachLine('\t') {
  if(!sentiments.containsKey(it[1])) {
    sentiments[it[1]] = [:]
  }
  it[5].tokenize(',').each { s ->
    def v = s.tokenize(':')
    //println v
    if(v.size() == 3 && v[0] != 'SentimentClass') {
      if(!sentiments[it[1]].containsKey(v[0])) {
        sentiments[it[1]][v[1]] = []
      }
      sentiments[it[1]][v[1]] << Double.parseDouble(v[2])
    }
  }
}

def averages = [:]
sentiments.each { k, entries ->
  averages[k] = [:]
  entries.each { cl, vals ->
    averages[k][cl] = vals.sum() / vals.size() 
  }
}

averages.each { k, vals ->
  def savgstr = vals.collect { kk, vv -> "$kk:$vv" }.join(',')
  println "${k}\t${savgstr}"
}
