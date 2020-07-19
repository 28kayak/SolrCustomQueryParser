import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;

public class ExactPhraseQParserPlugin extends QParserPlugin {
    @Override
    public QParser createParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        System.out.printf("[ExactPhraseQParserPlugin] createParser is called {qstr = %s; localParams = %s; params = %s ; req = %s}\n",
                qstr, localParams.toLocalParamsString(), params.toLocalParamsString(),req.getParamString() );

        return new ExactPhraseQParser(qstr, localParams, params, req);
    }
}
