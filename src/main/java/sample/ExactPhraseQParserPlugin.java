package sample;

import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class ExactPhraseQParserPlugin extends QParserPlugin {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Override
    public QParser createParser(String qstr, SolrParams localParams, SolrParams params, SolrQueryRequest req) {
        //log.error("[parser.ExactPhraseQParserPlugin] createParser is called; qstr={}", qstr);
        System.out.printf("[parser.ExactPhraseQParserPlugin] createParser is called {qstr = %s;}\n", qstr );
        System.out.printf("[parser.ExactPhraseQParserPlugin] createParser is called {params = %s;}\n", params.toString() );
        return new ExactPhraseQParser(qstr, localParams, params, req);
    }
}
