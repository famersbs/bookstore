package com.netand.bookstore;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class NADIndexerLucene {	
	
	private Object synch = new Object();
	
	private String INDEX_DIR = "";
	private String SRC_DIR = "";
	
	private Analyzer analyzer = null;
	private Directory directory = null;
	private IndexWriter iwriter = null; 
	
	public NADIndexerLucene( String index_dir, String src_dir )
	{
		INDEX_DIR = index_dir;
		SRC_DIR = src_dir;
	}
	/*
	private static NADIndexerLucene instance = null;
	
	public static synchronized NADIndexerLucene getInstance( String index_dir, String src_dir ){
	
		if( null == instance ){
			instance = new NADIndexerLucene(index_dir, src_dir);
			instance.init();
		}
		return instance;
	}
	*/

	public boolean init()
	{
		analyzer = new StandardAnalyzer(Version.LUCENE_35);		
		return true;
	}

	
	private Directory getDirectory()
	{
		File idx_file 	= new File( INDEX_DIR );
		
		try{
			FSDirectory fsdir = FSDirectory.open( idx_file );
			return fsdir;
		}catch( Exception e )
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public IndexWriter openWriter(){
		directory = getDirectory();
		
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		
		try {
			iwriter = new IndexWriter(directory, config );
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return iwriter;
	}
	
	public void closeWriter( IndexWriter writer ){
		try {
			iwriter.commit();
			iwriter.close();
		    directory.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private boolean createIndex( String filename, String category, String contents ){
		try{
			synchronized( synch ){
				
				directory = getDirectory();
				
				IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);
				
				iwriter = new IndexWriter(directory, config );
				
				
				if( null == iwriter ){
					return false;
				}
				
				Document doc = new Document();
				
			    doc.add(new Field(VO_NBS.CONTENTS, contents, Field.Store.YES, Field.Index.ANALYZED));
			    doc.add(new Field(VO_NBS.CATEGORY, category , Field.Store.YES, Field.Index.ANALYZED ));
			    doc.add(new Field(VO_NBS.FILENAME, filename , Field.Store.YES, Field.Index.ANALYZED ));
			    
			    iwriter.addDocument(doc);
			    iwriter.commit();
			    
			    iwriter.close();
			    directory.close();
			}
		}catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean addDocument( String filename, String category )
	{
		String text = loadString( SRC_DIR + filename );
		if( null == text ) return false;
		return createIndex( filename, category, text );
	}
	
	private String loadString( String filename ){
		File f = new File( filename );
		if( ! f.canRead() ){
			return "";
		}
		try{
			
			if( f.getName().toLowerCase().lastIndexOf(".pdf") > 0  )
			{
				PDDocument doc = PDDocument.load(f);
				PDFTextStripper stripper = new PDFTextStripper();
				String txt = stripper.getText(doc);
				return txt;
			}else{
				FileReader fr = new FileReader(f);
			    char[] cbuf = new char[1024];
			    StringBuffer buf = new StringBuffer();
			    while( -1 != fr.read(cbuf) ){
			    	buf.append( cbuf );
			    }
				return buf.toString();
			}
			
		}catch( Exception e ){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	private COSDocument parsePDFDocument( InputStream is ) throws IOException
	{
		PDFParser parser = new PDFParser( is );
		parser.parse();
		return parser.getDocument();
	}
	*/
	/*
	public List<VO_NBS> getDocument( String query_str )
	{
		String fieldname = VO_NBS.CONTENTS;
		
		LinkedList<VO_NBS> obj_list = new LinkedList<VO_NBS>();
		
		try{
			
			directory = getDirectory();
			
			IndexReader ireader = IndexReader.open(directory); // read-only=true
			IndexSearcher isearcher = new IndexSearcher(ireader);
			// Parse a simple query that searches for "text":
			QueryParser parser = new QueryParser(Version.LUCENE_35, fieldname, analyzer);
			Query query = parser.parse( query_str );
			ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
//		    assertEquals(1, hits.length);
			// Iterate through the results:
			
			VO_NBS vo = null;
			
			for (int i = 0; i < hits.length; i++) {
			  Document hitDoc = isearcher.doc(hits[i].doc);
			  
			  vo = new VO_NBS();
			  
			  vo.setContents( hitDoc.get(VO_NBS.CONTENTS) );
			  vo.setCategory( hitDoc.get(VO_NBS.CATEGORY) );			  
			  vo.setFilename( hitDoc.get(VO_NBS.FILENAME) );
		
			  obj_list.add(vo);
			}
			isearcher.close();
			ireader.close();
			
			directory.close();
			
		}catch( Exception e )
		{
			e.printStackTrace();
		}
		
		return obj_list;
	}
	*/
	
	private static int LIMIT_TOP_SEARCH_DOC_COUNT = 100;
	private static int LIMIT_FLAG_COUNT = 1;
	
	public List<VO_NBS> getDocumentHighlight( String query_str )
	{
		String fieldname = VO_NBS.CONTENTS;
		
		LinkedList<VO_NBS> obj_list = new LinkedList<VO_NBS>();
		
		try{
			
			directory = getDirectory();
			
			IndexReader ireader = IndexReader.open(directory); // read-only=true
			IndexSearcher isearcher = new IndexSearcher(ireader);
			// Parse a simple query that searches for "text":
			QueryParser parser = new QueryParser(Version.LUCENE_35, fieldname, analyzer);
			Query query = parser.parse( query_str );
			
			TopDocs topHits = isearcher.search(query, LIMIT_TOP_SEARCH_DOC_COUNT);
			
			SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
			Highlighter highlighter = new Highlighter( htmlFormatter , new QueryScorer( query ));
			
			VO_NBS vo = null;
			
			for (int i = 0; i < topHits.scoreDocs.length ; i++) {
				
			    int id = topHits.scoreDocs[i].doc;
			    Document doc = isearcher.doc(id);
			    String text = doc.get(fieldname);
			    TokenStream tokenStream = TokenSources.getAnyTokenStream(isearcher.getIndexReader(), id, fieldname, analyzer);
			    TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, text, false, 10);//highlighter.getBestFragments(tokenStream, text, 3, "...");
			    
			    int maxCnt = Math.min( LIMIT_FLAG_COUNT , frag.length) ;
			    for (int j = 0; j < maxCnt; j++) {
			    	if ((frag[j] != null) && (frag[j].getScore() > 0)) {
      
			    		vo = new VO_NBS();
			    		vo.setHighlight( frag[j].toString() ); 
			    		vo.setCategory(doc.get(VO_NBS.CATEGORY));
			    		vo.setFilename(doc.get(VO_NBS.FILENAME));
			    		obj_list.add(vo);
			    		//System.out.println((frag[j].toString()));
			    	}
			    }
  
			    //System.out.println("-------------");
			}
			
			isearcher.close();
			ireader.close();
			
			directory.close();
			
		}catch( Exception e )
		{
			e.printStackTrace();
		}
		
		return obj_list;
	}
	
	
	
	
	
	
	public void close(){			
	}	
}



