import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.search.Query;
import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.core.SolrCore;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.junit.BeforeClass;
import org.junit.Test;
import sample.ExactPhraseQParserPlugin;

import java.io.File;



public class TestExactPhraseQParserPlugin extends SolrTestCaseJ4 {
    private static Logger LOGGER = null;

    @BeforeClass
    public static void beforeClass() throws Exception {
        String testDir = System.getProperty("testFilesDir");
        System.setProperty("log4j.configurationFile", "collection1/log4j2-testConfig.xml");
        LOGGER = LogManager.getLogger();
        File solrHome =  new File(testDir, "/src/test/resources/");
        System.out.println(solrHome.getAbsolutePath());
        //String tmpSolrHome = createTempDir(solrHome.getAbsolutePath() + "/temp").toFile().getAbsolutePath();
        //String tempSolrHome = createTempDir(solrHome.getAbsolutePath() + "/temp").toFile().getAbsolutePath();
        File tempFile = new File(solrHome.getPath()).getAbsoluteFile();

        //FileUtils.copyDirectory(solrHome, tempFile);
        System.out.println(tempFile.getAbsolutePath());

        initCore("collection1/solrconfig.xml", "collection1/schema.xml", tempFile.getAbsolutePath() );

        //User Configuration Under the resources/collection1

        //initCore("collection1/schema.xml", "collection1/schema.xml",tempFile.getAbsolutePath());
        //initCore();

        //initCore("solrconfig.xml", "schema.xml");
        assertU(adoc("id", "1", "t_title", "The Magicians", "text", " Licensed to the Apache Software Foundation (ASF) under one or more\n" +
                " contributor license agreements. "));
        assertU(adoc("id", "2", "t_title", "The Eye of the World", "text", "this work for additional information regarding copyright ownership."));
        assertU(adoc("id", "3", "t_title", "The Great Hunt", "text","you may not use this file except in compliance with\n" +
                " the License.  "));
        //assertU(adoc("id","1", "author_s", "Lev Grossman", "t_title", "The Magicians",  "cat_s", "fantasy", "pubyear_i", "2009"));
        //assertU(adoc("id", "2", "author_s", "Robert Jordan", "t_title", "The Eye of the World", "cat_s", "fantasy", "cat_s", "childrens", "pubyear_i", "1990"));
        //assertU(adoc("id", "3", "author_s", "Robert Jordan", "t_title", "The Great Hunt", "cat_s", "fantasy", "cat_s", "childrens", "pubyear_i", "1990"));
        //assertU(adoc("id", "4", "author_s", "N.K. Jemisin", "t_title", "The Fifth Season", "cat_s", "fantasy", "pubyear_i", "2015"));
        //assertU(commit());
        //assertU(adoc("id", "5", "author_s", "Ursula K. Le Guin", "t_title", "The Dispossessed", "cat_s", "scifi", "pubyear_i", "1974"));
        //assertU(adoc("id", "6", "author_s", "Ursula K. Le Guin", "t_title", "The Left Hand of Darkness", "cat_s", "scifi", "pubyear_i", "1969"));
        //assertU(adoc("id", "7", "author_s", "Isaac Asimov", "t_title", "Foundation", "cat_s", "scifi", "pubyear_i", "1951"));
        //assertU(commit());
        System.out.println("documents are added");
    }
    SolrCore core;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        core = h.getCore();

        lrf = h.getRequestFactory("standard", 0, 20);
    }
    /*@Test
    public void createParserTest(){
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.add("q", "author_s:Isaac Asimov");
        params.add("defType", "exactphrase");
        //params.add("sort", "id asc");
        assertQ(req(params, "indent", "on"), " *** ");

    }*/
    @Test
    public void testParser() throws Exception{
        setUp();
        final String Q = "The Magicians";
        ExactPhraseQParserPlugin epqp = (ExactPhraseQParserPlugin) core.getQueryPlugin("exactphrase");
        SolrQueryRequest req = req("q", Q, "qf","t_title id", "defType", "exactphrase","df", "t_title");
        QParser qParser = epqp.createParser(Q, req.getParams(), req.getParams(),req);
        Query query = qParser.parse();

        System.out.println(query.toString());
        //assertEqualsWithQueryStringNormalizer();
    }
    /*@After
    public void clearIndex(){
        super.clearIndex();
    }*/
}
