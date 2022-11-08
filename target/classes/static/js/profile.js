/**
 * 
 */
let searchBtn = document.querySelector('.btn-srch');
let searchText = document.querySelector('.search');
let searchResultsCont = document.querySelector('.products-result');
let BasicInfo = document.querySelector(`.Basic-Info`);
let accountSec = document.querySelector(`.account-sec`);
let profileContainer = document.querySelector(`.profile-container`);
let secContainer = document.querySelector(`.sec-container`);
let newPass = document.getElementById(`new-pass`);
let rePass = document.getElementById(`re-pass`);
let newEmail = document.getElementById(`new-email`);
let saveBtn = secContainer.querySelector(`input[type="submit"]`);

rePass.addEventListener(`input`, checkPasswords);
newPass.addEventListener(`input`, checkPasswords);
function checkPasswords() {
	let matchedPass = document.querySelector(`.matched-pass`);
	console.log(`newPass value ${newPass.value} , rePass value ${rePass.value}`);
	if (((newPass.value != `` && rePass.value != ``) && newPass.value != rePass.value)) {
		if (matchedPass == null) {
			let matchedSpan = document.createElement(`span`);
			matchedSpan.className = `matched-pass`;
			matchedSpan.textContent = `passwords Not Identical`;
			newPass.after(matchedSpan);
		}

	}
	else {
		if (matchedPass != null) {
			matchedPass.remove();
		}
	}
}
saveBtn.addEventListener(`click`, (e) => {
	if (!(newPass.value != `` && rePass.value != `` && newPass.value == rePass.value)) {
		e.preventDefault();
		let matchedPass = document.querySelector(`.matched-pass`);
		if (matchedPass == null) {
			let matchedSpan = document.createElement(`span`);
			matchedSpan.textContent = `passwords Not Identical`;
			newPass.after(matchedSpan);
		}


	}
	let matchedEmail = document.querySelector(`.matched-email`);
	if (matchedEmail != null) {
		e.preventDefault();
	}
});
let searchBtnOnClickFun = function srcbtnOnClick(e) {

	if (searchText.value.length != 0)
		location.href = `http://localhost:8080/products/name/${searchText.value}`;
};

let searchOnVal = function(e) {
	removePrevResultDivs();
	if (searchText.value.length != 0) {
		let url = `http://localhost:8080/rest/api/products/product/${searchText.value}`;
		fetch(url).then((rs) => {

			rs.json().then((rs) => {
				console.log(rs.length);

				if (rs.length > 0) {
					rs.length = rs.length > 7 ? 7 : rs.length;
					createResultDivs(rs);
				}

			})
				.catch((e => { console.log("Error ........") }));

		});

	}

};
searchBtn.addEventListener('click', searchBtnOnClickFun);

searchText.addEventListener('input', searchOnVal);

function createResultDivs(arr) {
	searchResultsCont.children = 0;
	searchResultsCont.style.visibility = `hidden`;
	searchResultsCont.style.width = `80%`;

	for (let i = 0; i < arr.length; i++) {
		let resultItem = document.createElement('div');
		let resultText = document.createTextNode(arr[i].name);
		resultItem.style.height = `40px`;
		resultItem.append(resultText);
		resultItem.classList.add('result-item');
		resultItem.setAttribute('proId', arr[i].id);
		searchResultsCont.append(resultItem);

	}
	searchResultsCont.style.visibility = `visible`;
}

// remove previous search  result divs 
function removePrevResultDivs() {
	searchResultsCont.innerHTML = "";
	searchResultsCont.style.width = `0`;
	searchResultsCont.style.visibilty = 'hidden';
}

document.addEventListener('click', (e) => {
	if (e.target.hasAttribute("class") && e.target.getAttribute("class") == 'result-item') {
		let uri = `http://localhost:8080/products/id/${e.target.getAttribute('proId')}`;
		console.log(uri);
		location.href = uri;
	}
});

accountSec.addEventListener(`click`, (e) => {
	profileContainer.style.display = `none`;
	secContainer.style.display = `block`;
});

BasicInfo.addEventListener(`click`, (e) => {
	secContainer.style.display = `none`;
	profileContainer.style.display = `block`;
});

if (location.href == `http://localhost:8080/changeInfo`) {
	accountSec.click();
}


newEmail.addEventListener(`blur`, async () => {
	let uri = `http://localhost:8080/rest/api/user/checkEmail/${newEmail.value}`;
	let bool = await fetch(uri).then((rs) => {
		return rs.json();
	});
	let matchedEmail = document.querySelector(`.matched-email`);
	if (!bool) {
		if (matchedEmail == null) {
			let matchedEmail = document.createElement(`span`);
			matchedEmail.className = `matched-email`;
			matchedEmail.textContent = `Email Is Already In Use`;
			newEmail.before(matchedEmail);
			//	newPass.after(matchedSpan);
		}
	}
	else {
		if (matchedEmail != null) {
			matchedEmail.remove();
		}

	}
});








































