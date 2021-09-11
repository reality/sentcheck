@Grab('org.apache.commons:commons-csv:1.2')
import org.apache.commons.csv.CSVParser
import static org.apache.commons.csv.CSVFormat.*

def mimicDirectory = new File(args[0])
def records = [:]
new File(mimicDirectory, 'NOTEEVENTS.csv').withReader { reader ->
  CSVParser csv = new CSVParser(reader, DEFAULT.withHeader())

  for(record in csv.iterator()) {
  /*println record['CATEGORY']
  println record['HADM_ID']*/
    if(record['CATEGORY'] == 'Discharge summary') {
      if(!records.containsKey(record['HADM_ID'])) {
        records[record['HADM_ID']] = []
      }
      records[record['HADM_ID']] << record
      println 'Added new record to ' + record['HADM_ID']
    }
  }
}

def outDir = new File('texts')
if(!outDir.exists()) { outDir.mkdir() }

records.each { k, v ->
  new File(outDir, k + '.txt').text = v.collect { it['TEXT'].replaceAll('\n', ' ').replaceAll('\\s+', ' ').replaceAll('\\.', '. ') }
}

