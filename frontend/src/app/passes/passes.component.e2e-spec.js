describe('Passes', function () {

  beforeEach(function () {
    browser.get('/passes');
  });

  it('should have <my-passes>', function () {
    var passes = element(by.css('my-app my-passes'));
    expect(passes.isPresent()).toEqual(true);
    expect(passes.getText()).toEqual("Passes Page!");
  });

});
