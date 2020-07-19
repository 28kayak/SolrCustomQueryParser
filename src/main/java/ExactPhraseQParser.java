import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SyntaxError;

import java.io.IOException;

/*
* QParser to do:
* 1. read the field name
* 2. get the field's query analyzer
* 3. tokenize the query string with that analyzer
* 4. build a phrase query with each token's text
* */


public class ExactPhraseQParser extends QParser {
    private String fieldName;

    @Override
    public Query parse() throws SyntaxError {
        System.out.println("[ExactPhraseQParser] parse is called");
        //get the field's query analyzer
        Analyzer analyzer = req.getSchema().getQueryAnalyzer();

        //Tokenize the given phrase with the given field's query-time analyzer
        PhraseQuery.Builder builder = new PhraseQuery.Builder();
        try(TokenStream ts = analyzer.tokenStream(fieldName, qstr)){
            //Returns the instance of the passed in Attribute contained in this AttributeSource
            CharTermAttribute termAttribute = ts.getAttribute(CharTermAttribute.class);

            ts.reset();
            while (ts.incrementToken()){
                //add each token's text to the phrase
                System.out.println("termAttribute.toString() = " + termAttribute.toString());
                builder.add(new Term(fieldName, termAttribute.toString()));
            }
            return builder.build();

        }catch (IOException exception){
            throw new SyntaxError(exception);
        }
    }//parse
    public ExactPhraseQParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req){

        super(qstr,localParams,params,req);
        System.out.println("In ExactPhraseQParser: after super");
        fieldName = localParams.get("field");
        if (fieldName == null) {
            System.out.println("Field name was null");
            fieldName = params.get("df");
        }
        System.out.println("End of constructor: fieldName = " + fieldName);
    }//constructor

}//class
