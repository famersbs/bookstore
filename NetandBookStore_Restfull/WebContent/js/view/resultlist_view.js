var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// APP VIEW!!!
	app.ResultListView = Backbone.View.extend({

		// Instead of generating a new element, bind to the existing skeleton of
		// the App already present in the HTML.
		el: '#resultlist_view',
		
		// 단일 아이템에 대한 템플릿 함수를 캐쉬한다.
	    template: _.template( $('#resultlist_template').html() ),
	    
	    
		initialize: function() {
			
			app.bookList.on( 'reset', this.onReset, this );
			app.bookList.on( 'add', this.addAll, this );
		    
		},

		// Re-rendering the App just means refreshing the statistics -- the rest
		// of the app doesn't change.
		render: function() {
			//this.$el.html( this.template() );
		    return this;
		},
		
		
		addOne: function( element ) {
	      var view = new app.ResultView({ model: element });
	      console.log( view.render().el );
	      $('#result-list').append( view.render().el );
	    },
		    
		/// evenet listner
		onReset: function(){
			this.$el.html( this.template() );
		    app.bookList.each( function(model){ console.log( "is Reset " + model ); } , this);
		},
		
		addAll: function(){
			this.$el.html( this.template() );
		    app.bookList.each( function(model){ this.addOne(model); } , this);
		},

	});
	
});