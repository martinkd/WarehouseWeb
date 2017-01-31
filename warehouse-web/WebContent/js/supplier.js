var app = angular.module('app1', []);

app.controller('ctrl1', function($scope, $http) {
	$scope.items;
	$scope.getAll = function() {
		$http.get('supplier').success(function(response) {
			$scope.items = response;
		});
	}
	$scope.search = function(searchName) {
		$http.get("supplier?searchName=" + searchName).success(
				function(response) {
					$scope.items = response;
					$scope.searchName = "";
				});
	}

	$scope.editForm = false;
	
	$scope.openAddForm = function() {
		$scope.name = "";
		$scope.quantity = "";
		$scope.price = "";
		$scope.editForm = true;
		$scope.addButton = true;
		$scope.updateButton = false;
	}
	
	$scope.openEditForm = function(item) {
		$scope.id = item.id;
		$scope.name = item.name;
		$scope.quantity = item.quantity;
		$scope.price = item.price;
		$scope.editForm = true;
		$scope.updateButton = true;
		$scope.addButton = false;
	}
	
	$scope.add = function() {
		var itemData = {
			name : $scope.name,
			quantity : $scope.quantity,
			price : $scope.price
		};

		$http({
			url : 'supplier',
			method : "POST",
			params : itemData
		}).success(function() {
			alert("New item added");
			$scope.getAll();
		});
		
		$scope.name="";
		$scope.quantity="";
		$scope.price="";
	}
	
	$scope.update = function(id) {
		var itemData = {
			id : id,
			name : $scope.name,
			quantity : $scope.quantity,
			price : $scope.price
		};

		$http({
			url : 'supplier',
			method : "PUT",
			params : itemData
		}).success(function() {
			alert("Updated");
			$scope.getAll();
		});
		
		$scope.name="";
		$scope.quantity="";
		$scope.price="";
	}

	$scope.remove = function(item) {
		var itemData = {
			toRemove : item.id
		};
		$http({
			method : 'DELETE',
			url : 'supplier',
			params : itemData
		}).then(function() {
			alert("Item deleted");
			$scope.getAll();
		});
	}

});