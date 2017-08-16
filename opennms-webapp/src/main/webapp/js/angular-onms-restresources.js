(function() {
	'use strict';

	// Base URL of the REST service
	var BASE_REST_URL = 'api/v2';

	// Module name
	var MODULE_NAME = 'onms.restResources';

	/**
	 * Function used to append an extra transformer to the default $http transforms.
	 * 
	 * @param defaultTransform Existing response transformer(s)
	 * @param transform New transformer to append to the end of the list
	 */
	function appendTransform(defaultTransform, transform) {
		defaultTransform = angular.isArray(defaultTransform) ? defaultTransform : [ defaultTransform ];
		return defaultTransform.concat(transform);
	}

	/**
	 * Ensure that the REST responses are always returned as arrays
	 * 
	 * @param data HTTP response
	 * @param headers HTTP response headers
	 * @param status HTTP response status code
	 * @param key Name of the key where values are stored in {@code data}
	 */
	function arrayify(data, headers, status, key) {
		// TODO: Figure out how to handle session timeouts that redirect to 
		// the login screen
		/*
		if (status === 302) {
			$window.location.href = $location.absUrl();
			return [];
		}
		*/
		if (status === 204) { // No content
			return [];
		} else {
			// Always return the data as an array
			return angular.isArray(data[key]) ? data[key] : [ data[key] ];
		}
	}

	// REST $resource module
	angular.module(MODULE_NAME, [ 'ngResource' ])

	// OnmsAlarm REST $resource
	.factory('alarmFactory', function($resource, $log, $http, $location) {
		return $resource(
			BASE_REST_URL + '/alarms/:id', 
			{ id: '@id' },
			{
				'query': { 
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'alarm');
					})
				},
				'update': { 
					method: 'PUT'
				},
				'queryProperties': {
					url: BASE_REST_URL + '/alarms/properties',
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'searchProperty');
					})
				},
				'queryPropertyValues': {
					url: BASE_REST_URL + '/alarms/properties/:id',
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'value');
					})
				}
			}
		);
	})

	// OnmsEvent REST $resource
	.factory('eventFactory', function($resource, $log, $http, $location) {
		return $resource(
			BASE_REST_URL + '/events/:id', 
			{ id: '@id' },
			{
				'query': { 
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'event');
					})
				},
				'update': { 
					method: 'PUT'
				},
				'queryProperties': {
					url: BASE_REST_URL + '/events/properties',
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'searchProperty');
					})
				},
				'queryPropertyValues': {
					url: BASE_REST_URL + '/events/properties/:id',
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'value');
					})
				}
			}
		);
	})

	// OnmsMinion REST $resource
	.factory('minionFactory', function($resource, $log, $http, $location) {
		return $resource(BASE_REST_URL + '/minions/:id', { id: '@id' },
			{
				'query': { 
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'minion');
					})
				},
				'update': { 
					method: 'PUT'
				}
			}
		);
	})

	// OnmsMonitoringLocation REST $resource
	.factory('monitoringLocationFactory', function($resource, $log, $http, $location) {
		return $resource(BASE_REST_URL + '/monitoringLocations/:id', {},
			{
				'query': { 
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'location');
					})
				},
				'update': { 
					method: 'PUT'
				}
			}
		);
	})

	// OnmsNotification REST $resource
	.factory('notificationFactory', function($resource, $log, $http, $location) {
		return $resource(BASE_REST_URL + '/notifications/:id', { id: '@id' },
			{
				'query': { 
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'notification');
					})
				},
				'update': { 
					method: 'PUT'
				},
				'queryProperties': {
					url: BASE_REST_URL + '/notifications/properties',
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'searchProperty');
					})
				},
				'queryPropertyValues': {
					url: BASE_REST_URL + '/notifications/properties/:id',
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'value');
					})
				}
			}
		);
	})

	// OnmsOutage REST $resource
	.factory('outageFactory', function($resource, $log, $http, $location) {
		return $resource(BASE_REST_URL + '/outages/:id', { id: '@id' },
			{
				'query': { 
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'outage');
					})
				},
				'update': { 
					method: 'PUT'
				},
				'queryProperties': {
					url: BASE_REST_URL + '/outages/properties',
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'searchProperty');
					})
				},
				'queryPropertyValues': {
					url: BASE_REST_URL + '/outages/properties/:id',
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'value');
					})
				}
			}
		);
	})

	.factory('scanReportLogFactory', function($resource, $log, $http, $location) {
		return $resource(BASE_REST_URL + '/scanreports/:id/logs', { id: '@id' },
			{
				'query': { 
					method: 'GET',
					transformResponse: function(data, headers, status) {
						var ret;
						switch(status) {
							case 302: // refresh on redirect
								$window.location.href = $location.absUrl();
								ret = {};
								break;
							case 204: // no content
								ret = {};
								break;
							default:
								ret = {text:data};
						}
						//$log.debug('$resource(logs) returning: ' + angular.toJson(ret));
						return ret;
					}
				}
			}
		);
	})

	// ScanReport REST $resource
	.factory('scanReportFactory', function($resource, $log, $http, $location) {
		return $resource(BASE_REST_URL + '/scanreports/:id', { id: '@id' },
			{
				'query': { 
					method: 'GET',
					isArray: true,
					// Append a transformation that will unwrap the item array
					transformResponse: appendTransform($http.defaults.transformResponse, function(data, headers, status) {
						return arrayify(data, headers, status, 'scan-report');
					})
				},
				'update': { 
					method: 'PUT'
				}
			}
		);
	});

}());
