__webpack_public_path__ = loadAppUrl();

import React from "react";
import ReactDOM from "react-dom";
import HomeTemplatePage from "../pages/HomeTemplatePage";
import PushService from "alexandria-ui-elements/src/services/PushService";
import FileService from "alexandria-ui-elements/src/services/FileService";
import TranslatorService from "alexandria-ui-elements/src/services/TranslatorService";
import GoogleApiLoader from "alexandria-ui-elements/src/displays/components/geo/GoogleApiLoader";
import DisplayRouter from "alexandria-ui-elements/src/displays/DisplayRouter";

var launchApplication = function () {
	var configuration = loadConfiguration();

	window.Application = (function(configuration) {
		var self = {};

		self.configuration = configuration;
		self.configuration.appUrls = loadUrls(configuration.baseUrls);
		self.configuration.appUrl = (app) => {
			return self.configuration.appUrls[app] != null ? self.configuration.appUrls[app] : self.configuration.baseUrl;
		};
		self.services = {
			pushService: PushService,
			fileService: FileService.create(configuration),
			translatorService: TranslatorService.create(configuration)
		};

		return self;
	})(configuration);

	renderApplication();

	function loadConfiguration() {
		return document.configuration;
	}

	function loadUrls(urlList) {
		let result = {};
		if (urlList == null) return result;
		for (let i=0; i<urlList.length; i++) {
			const urlInfo = urlList[i].split("_##_");
			result[urlInfo[0]] = urlInfo[1];
		}
		return result;
	}

	function renderApplication() {
		const requireGoogleApi = Application.configuration.googleApiKey != null && Application.configuration.googleApiKey !== "";
		const content = requireGoogleApi ? <GoogleApiLoader onLoad={openPushService()}><HomeTemplatePage/></GoogleApiLoader> : <HomeTemplatePage/>;
		const homeTemplate = document.getElementById("HomeTemplate");
		if (homeTemplate) ReactDOM.render(<DisplayRouter id="__router__" owner={()=>""} context={()=>""}>{content}</DisplayRouter>, homeTemplate);
		if (!requireGoogleApi) openPushService();
	}

	function openPushService() {
		window.setTimeout(() => {
			const pushConnections = Application.configuration.pushConnections;
			for (let i=0; i<pushConnections.length; i++) {
				const connection = pushConnections[i].split("_##_");
				if (connection[0].toLowerCase() !== "default") continue;
				PushService.openConnection(connection[0], connection[1]);
			}
		}, 100);
	}

};

function loadAppUrl() {
	let url = window.location.pathname !== "/" ? window.location.pathname : "";
	if (url.lastIndexOf("/") > 0) url = url.substr(0, window.location.pathname.lastIndexOf('/'));
	if (url === "/") url = "";
	return url + "/demo1-elements/";
}

launchApplication();