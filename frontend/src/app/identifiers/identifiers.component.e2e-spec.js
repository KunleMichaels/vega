describe('Identifiers', function () {

  beforeEach(function () {
    browser.get('/identifiers');
  });

  it('should have <my-identifiers>', function () {
    var identifiers = element(by.css('my-app my-identifiers'));
    expect(identifiers.isPresent()).toEqual(true);
    expect(identifiers.getText()).toEqual("Identifiers Page!");
  });

});
