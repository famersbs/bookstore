var app = app || {};
var ENTER_KEY = 13;

$(function() {
	'use strict';
	
	////////////////////////////////////////////////////////////////
	// APP VIEW!!!
	app.ResultView = Backbone.View.extend({

		//... li 태그
	    tagName:  'li',
	    
		// Instead of generating a new element, bind to the existing skeleton of
		// the App already present in the HTML.
		//el: '#resultlist_view',
		
		// 단일 아이템에 대한 템플릿 함수를 캐쉬한다.
	    template: _.template( $('#result_template').html() ),
	    
	    
		initialize: function() {
			
		},

		// Re-rendering the App just means refreshing the statistics -- the rest
		// of the app doesn't change.
		render: function() {
			this.$el.html( this.template( this.model.toJSON() ) );
			//console.log("is resultiew " + this.template( this.model.toJSON() ) );
		    return this;
		},
		
		
		/// evenet listner
		onReset: function(){
			this.$el.html( this.template() );
			
		    app.bookList.each( function(model){ console.log( "is Reset " + model ); } , this);
		},
		
		addAll: function(){
			this.$el.html( this.template() );
			
		    app.bookList.each( function(model){ console.log( "is AddAll " +  model ); } , this);
		},

	});
	
});