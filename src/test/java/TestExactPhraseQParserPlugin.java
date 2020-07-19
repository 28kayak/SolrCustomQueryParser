import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.solr.SolrTestCaseJ4.*;


public class TestExactPhraseQParserPlugin {
    @BeforeClass
    public static void beforeClass() throws Exception {


        initCore("solrconfig.xml", "schema.xml");
        assertU(adoc("id","1", "author_s", "Lev Grossman", "t_title", "The Magicians",  "cat_s", "fantasy", "pubyear_i", "2009"));
        assertU(adoc("id", "2", "author_s", "Robert Jordan", "t_title", "The Eye of the World", "cat_s", "fantasy", "cat_s", "childrens", "pubyear_i", "1990"));
        assertU(adoc("id", "3", "author_s", "Robert Jordan", "t_title", "The Great Hunt", "cat_s", "fantasy", "cat_s", "childrens", "pubyear_i", "1990"));
        assertU(adoc("id", "4", "author_s", "N.K. Jemisin", "t_title", "The Fifth Season", "cat_s", "fantasy", "pubyear_i", "2015"));
        assertU(commit());
        assertU(adoc("id", "5", "author_s", "Ursula K. Le Guin", "t_title", "The Dispossessed", "cat_s", "scifi", "pubyear_i", "1974"));
        assertU(adoc("id", "6", "author_s", "Ursula K. Le Guin", "t_title", "The Left Hand of Darkness", "cat_s", "scifi", "pubyear_i", "1969"));
        assertU(adoc("id", "7", "author_s", "Isaac Asimov", "t_title", "Foundation", "cat_s", "scifi", "pubyear_i", "1951"));
        assertU(commit());
    }
    @Test
    public void createParserTest(){
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("q", "author_s:Isaac Asimov");
        params.add("defType", "exactphrase");
        //params.add("sort", "id asc");
        assertQ(req(params, "indent", "on"), " *** ");

    }
}
