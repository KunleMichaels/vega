// This shows a different way of testing a component, check about for a simpler one
import { Component } from '@angular/core';

import { TestBed } from '@angular/core/testing';

import { ActivityComponent } from './activity.component';

describe('Activity Component', () => {
  const html = '<my-activity></my-activity>';

  beforeEach(() => {
    TestBed.configureTestingModule({declarations: [ActivityComponent, TestComponent]});
    TestBed.overrideComponent(TestComponent, { set: { template: html }});
  });

  it('should ...', () => {
    const fixture = TestBed.createComponent(TestComponent);
    fixture.detectChanges();
    expect(fixture.nativeElement.children[0].textContent).toContain('Activity Page!');
  });

});

@Component({selector: 'my-test', template: ''})
class TestComponent { }
