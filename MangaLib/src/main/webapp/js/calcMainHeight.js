function calcMainHeight() {
    var headerHeight = getComputedStyle(document.getElementById('header')).height;
    var footerHeight = getComputedStyle(document.getElementById('footer')).height;
    document.getElementById('main').style.minHeight = 'calc(100vh - ' + headerHeight + ' - ' + footerHeight + ')';
    window.addEventListener('resize', calcMainHeight);
}
calcMainHeight();

