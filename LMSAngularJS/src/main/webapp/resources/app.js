var libraryModule = angular.module('libraryApp', ['ngRoute', 'ngCookies', 'ui.bootstrap']);

libraryModule.config(["$routeProvider", function($routeProvider){
	return $routeProvider.when("/",{
		redirectTo: "/home"
	}).when("/home", {
		templateUrl: "home.html"
	}).when("/admin", {
		templateUrl: "admin.html"
	}).when("/admin/author", {
		templateUrl: "admin/author.html"
	}).when("/admin/addAuthor", {
		templateUrl: "admin/addauthor.html"
	}).when("/admin/viewAuthors", {
		templateUrl: "admin/viewauthors.html"
	}).when("/admin/book", {
		templateUrl: "admin/book.html"
	}).when("/admin/addBook", {
		templateUrl: "admin/addbook.html"
	}).when("/admin/viewBooks", {
		templateUrl: "admin/viewbooks.html"
	})
}])


libraryModule.controller('authorCtrl', function($scope, $http, $window, $log, $route, $uibModal){	
	$http.get('http://localhost:8080/lms/admin/getAuthorCount').
	success(function(data){
		$scope.totalItems = data;
	});
	
	$http.get('http://localhost:8080/lms/admin/viewAuthors/1').
	success(function(data){
		$scope.authors = data;
	});
	
	$scope.currentPage = 1;
	$scope.maxSize = 5;
	
	$scope.setPage = function (pageNo) {
	    $scope.currentPage = pageNo;
	};
	
	$scope.pageChanged = function() {
		$http.get('http://localhost:8080/lms/admin/viewAuthors/' + $scope.currentPage).
		success(function(data){
			$scope.authors = data;
		});
	};
	
	$scope.addAuthor = function(){
		var author = {authorName: $scope.author.authorName};
		$http.post('http://localhost:8080/lms/admin/addAuthor', author).
			success(function(data){
				$window.location.href = "#/admin/viewAuthors";
		});
	};

	// edit author modal
	  $scope.animationsEnabled = true;

	  $scope.open = function (size, a) {

	    var modalInstance = $uibModal.open({
	      animation: $scope.animationsEnabled,
	      templateUrl: 'editAuthorModal.html',
	      controller: 'ModalInstanceCtrl',
	      size: size,
	      resolve: {
	        item: function () {
	          return a;
	        }
	      }
	    });
	    
	    // editAuthor
	    modalInstance.result.then(function (selectedItem) {
	      var author = { authorId : selectedItem.authorId, authorName : selectedItem.authorName };
	      console.log(author);
			$http.post('http://localhost:8080/lms/admin/editAuthor', author).
				success(function(data){
					$route.reload();
					//$scope.message = "<div class='alert alert-success'>" + data + "</div>";
				});
	    });
	  };

	  $scope.toggleAnimation = function () {
	    $scope.animationsEnabled = !$scope.animationsEnabled;
	  };

})

libraryModule.controller("ModalInstanceCtrl", function ($scope, $uibModalInstance, item) {

	  $scope.item = item;
	  $scope.selected = item;
	  $scope.ok = function (selected) {
		    $uibModalInstance.close(selected);
		  };

	  $scope.cancel = function () {
	    $uibModalInstance.dismiss('cancel');
	  };
	});


libraryModule.controller('bookCtrl', function($scope, $http, $window, $route, $uibModal){
	$http.get('http://localhost:8080/lms/admin/viewBooks/0').
	success(function(data){
		$scope.books = data;
	});
	
	$http.get('http://localhost:8080/lms/admin/addBook').
	success(function(data){
		$scope.book = data;
		$scope.authors = $scope.book.authors;
		$scope.publishers = $scope.book.publishers;
		$scope.genres = $scope.book.genres;
	});
	
	$scope.addBook = function(){
		var book = { title: $scope.b.title, 
				authors: $scope.selectedAuthor, 
				publisher: $scope.selectedPub,
				genres: $scope.selectedGenre };
		console.log(book);
		
		$http.post('http://localhost:8080/lms/admin/addBook', book).
		success(function(data){
			$window.location.href = "#/admin/viewBooks";
		});
	}
	
	// edit book modal
	  $scope.animationsEnabled = true;

	  $scope.open = function (size, b) {

	    var modalInstance = $uibModal.open({
	      animation: $scope.animationsEnabled,
	      templateUrl: 'editBookModal.html',
	      controller: 'ModalInstanceCtrl',
	      size: size,
	      resolve: {
	        item: function () {
	        	$http.get('http://localhost:8080/lms/admin/addBook').
	        	success(function(data){
	        		$scope.book = data;
	        		b.editAuthors = $scope.book.authors;
		        	b.editPublishers = $scope.book.publishers;
		        	b.editGenres = $scope.book.genres;
	        	});
	        	b.selectedPub = b.publisher;
	        	b.selectedAuthors = b.authors;
	        	b.selectedGenres = b.genres;
	          return b;
	        }
	      }
	    });
	    
	    // editBook
	    modalInstance.result.then(function (selectedItem) {
	    	var book = { 
					bookId : selectedItem.bookId,
					title : selectedItem.title,
					publisher : selectedItem.selectedPub,
					authors : selectedItem.selectedAuthors,
					genres : selectedItem.selectedGenres					
				};
	      //var author = { authorId : selectedItem.authorId, authorName : selectedItem.authorName };
			$http.post('http://localhost:8080/lms/admin/editBook', book).
				success(function(data){
					$route.reload();
					//$scope.message = "<div class='alert alert-success'>" + data + "</div>";
				});
	    });
	  };

	  $scope.toggleAnimation = function () {
	    $scope.animationsEnabled = !$scope.animationsEnabled;
	  };
	
})
