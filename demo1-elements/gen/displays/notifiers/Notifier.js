export default class Notifier {
	static subscriptions = [];

	constructor(element) {
		this.element = element;
		this.pushService = Application.services.pushService;
		this.pushRegistrations = [];
		this.pushLinked = false;
	};

	setup() {
		var context = this.element.props.context != null ? this.element.props.context() : "";
		var key = this.element.props.id + context;
		//if (Notifier.subscriptions[key]) return;
		//Notifier.subscriptions[key] = true;
		this.when("addInstance").toSelf().execute((parameters) => this.element.addInstance(parameters));
		this.when("addInstances").toSelf().execute((parameters) => this.element.addInstances(parameters));
		this.when("insertInstance").toSelf().execute((parameters) => this.element.insertInstance(parameters));
		this.when("insertInstances").toSelf().execute((parameters) => this.element.insertInstances(parameters));
		this.when("removeInstance").toSelf().execute((parameters) => this.element.removeInstance(parameters));
		this.when("clearContainer").toSelf().execute((parameters) => this.element.clearContainer(parameters));
		this.when("redirect").toSelf().execute((parameters) => this.element.redirect(parameters));
		this.when("addressed").toSelf().execute((parameters) => this.element.addressed(parameters));
		this.when("closeClient").toSelf().execute((parameters) => this.element.closeClient(parameters));
	};

	when = (message) => {
		var register = _register.bind(this);
		var element = this.element;
		var pushService = this.pushService;
		return {
			toSelf: function () {
				return {
					execute: function (action) {
						register(pushService.listen(message, function (parameters) {
							let id = parameters.i;
							let ownerPath = parameters.o;
							let context = element.props.context != null ? element.props.context() : null;
							let elementId = element.shortId();
							if (id === elementId && (ownerPath == null || ownerPath === "" || ownerPath === context || _containsAll(ownerPath, context))) {
								action(parameters);
							}
						}));
					}
				}
			},
			execute: function (action) {
				register(pushService.listen(message, function(parameters) {
					if (parameters.n === element.name) {
						action(parameters);
					}
				}));
			}
		};

		function _containsAll(owner, context) {
			if (context == null) return false;
			let contextList = context.split(".");
			let ownerList = owner.split(".");
			return ownerList.length > contextList.length ? _containsInList(owner, contextList) : _containsInList(context, ownerList);
		}

		function _containsInList(content, list) {
			for (var i=0; i<list.length; i++) {
				if (content.indexOf(list[i]) === -1) return false;
			}
			return true;
		}

		function _register(registration) {
			this.pushRegistrations.push(registration);
		}
	};

	detached = () => {
		var context = this.element.props.context != null ? this.element.props.context() : "";
		var key = this.element.props.id + context;
		//Notifier.subscriptions[key] = null;
		this.pushRegistrations.forEach((registrations) => registrations.deregister());
		this.pushRegistrations.splice(0, this.pushRegistrations.length);
	};
}