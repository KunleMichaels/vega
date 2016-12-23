describe('Activity', function () {

  beforeEach(function () {
    browser.get('/activity');
  });

  it('should have <my-activity>', function () {
    var home = element(by.css('my-app my-activity'));
    expect(home.isPresent()).toEqual(true);
    expect(home.getText()).toEqual("Activity Page!");
  });

});
