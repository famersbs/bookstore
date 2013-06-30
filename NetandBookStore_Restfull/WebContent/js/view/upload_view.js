var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// APP VIEW!!!
	app.UploadView = Backbone.View.extend({

		// Instead of generating a new element, bind to the existing skeleton of
		// the App already present in the HTML.
		el: '#upload_view',
		
		// 항목에 지정된 DOM 이벤트
	    events: {
	      'click #close': 'Destory',
	      'click #submit': 'onSubmit'
	    },
		
		// 단일 아이템에 대한 템플릿 함수를 캐쉬한다.
	    template: _.template( $('#upload_template').html() ),
	    
	    
		initialize: function() {

		},

		// Re-rendering the App just means refreshing the statistics -- the rest
		// of the app doesn't change.
		render: function() {
			this.$el.html( this.template() );
		    return this;
		},
		
		/////////////
		// Dialog Box
		parent:null,
		close_callback:null,
		
		Show: function( parent, close_callback ){
			this.$el.removeClass( "upload_deactive" );
			this.$el.addClass( "upload_active" );
			this.render();
			
			this.parent = parent;
			this.close_callback = close_callback;
		},
		
		Destory: function(){
			
			this.$el.removeClass( "upload_active" );
			this.$el.addClass( "upload_deactive" );
			//this.$el.html( "" );
			this.$el.empty();
			
			if( null != this.close_callback && undefined != this.close_callback ){
				this.close_callback.apply( this.parent );
			}
			
		},
		
		
		///////////
		// onsubmit
		onSubmit: function(){
			
			
			/*
			 <form id="upload_form" action="./book" enctype="multipart/form-data" method="post"  >
			제목 : 
				<input type="text" id="title" name="title"></input><br/>
				<input type="file" name="book"> <br/>
				<input type="button" value="Upload" id="submit"></input>
				<input type="button" value="close" id="close"></input>
			</form>
			 */
			/*
			var book  = $("#book").val();
			var title = $("#title").val();
			
			
			var photo = $("#photo").val();
		    $.post("./book",{"book":book , "title":title },function(data){
		    	alert(data);
		     });
		    */
			
			this.Destory();
		}
		
		
	});
	
});