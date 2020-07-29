function showOptionForSvg(t,i){
	// vuem.showOption(vuem.stepList[0])
	// if(event.target){

	// }
	console.log(i)
	if(vuem.stepOptionVisible == false){
		let x = event.clientX;
		let y = event.clientY;
		let _popover = document.getElementsByClassName('el-popover') 
		if(_popover.length > 0){
			_popover[0].style.position = 'fixed'
			_popover[0].style.left = (x - 60) + 'px'
			_popover[0].style.top = (y + 30) + 'px'
		}
		vuem.stepOptionVisible = true
		vuem.currentStep = vuem.stepList[i];
	}else{
		vuem.stepOptionVisible = false
	}
}
