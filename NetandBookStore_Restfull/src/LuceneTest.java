import java.util.Iterator;
import java.util.List;

import com.netand.bookstore.NADIndexerLucene;
import com.netand.bookstore.VO_NBS;
import com.netand.bookstore.worker.WorkManager;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.*;

public class LuceneTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
//		if( args.length != 2 ){
//			throw new Exception( "Usage : java " + LuceneTest.class.getName() + " <index dir> <data dir>" );
//			
//		}
//		File indexDir = new File( args[0] );
//		File dataDir = new File( args[1] );
//		
		
		String INDEX_DIR = "./data/index/";
		String SRC_DIR = "./data/docs/";
		String filename = args[0];
		String category = args[1];
		NADIndexerLucene lucene_indexer = null;
		try{
			lucene_indexer = WorkManager.init(1, SRC_DIR, INDEX_DIR ).getLucene();
						
			lucene_indexer.addDocument( filename , category);
			
//			List<VO_NBS> list = lucene_indexer.getDocument( "select" );
//			Iterator<VO_NBS> i = list.iterator();
//			while( i.hasNext() ){
//				VO_NBS obj = i.next();
//				System.out.println( obj.getFilename() );
//				
//				
//			}

			List<VO_NBS> list = lucene_indexer.getDocumentHighlight( "select" );
			Iterator<VO_NBS> i = list.iterator();
			while( i.hasNext() ){
				VO_NBS obj = i.next();
				System.out.println( obj.getFilename() );
				System.out.println( obj.getHighlight() );
				
				
			}
			
			
		}catch( Exception e )
		{
			e.printStackTrace();
		}finally{
			if( null != lucene_indexer )
				lucene_indexer.close();
		}
	}
}
