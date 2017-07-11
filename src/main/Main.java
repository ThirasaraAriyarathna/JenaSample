package main;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

public class Main {
    private static final String RDF_PREFIX = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
    private static final String FOAF_PREFIX = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>";
    
    public static void main(String args[]){
        //Reading a RDF/XML file using the FOAF Ontology
        //FOAF ontology: http://xmlns.com//foaf/spec/
        
        sparqlTest();
        
        //model.write(System.out,"RDF/XML");
        
    }
    
    //this method loads the rdf file and runs a sparql query on that and print results
    public static void sparqlTest(){
        
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        Model model = FileManager.get().loadModel("/home/thirasara/workspace/jena-app/src/main/data.rdf");
        
        String sparqlString = 
                RDF_PREFIX +
                FOAF_PREFIX +
                "SELECT * WHERE { " +
                "?person foaf:name ?name." +
                "?person foaf:knows ?someone." +
                "?someone foaf:name ?name1." +
                "FILTER(?name1 = \"prabodha\")." +
                "}";
        Query query = QueryFactory.create(sparqlString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
            while(results.hasNext()){
                QuerySolution soln = results.nextSolution();
                Literal name = soln.getLiteral("name");
                System.out.println(name);
            }
        }
        finally{
            qexec.close();
        }
        
    }

}
