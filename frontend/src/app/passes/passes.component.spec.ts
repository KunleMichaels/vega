// This shows a different way of testing a component, check about for a simpler one
import { Component } from '@angular/core';

import { TestBed } from '@angular/core/testing';

import { PassesComponent } from './passes.component';

describe('Passes Component', () => {
  const html = '<my-passes></my-passes>';

  beforeEach(() => {
    TestBed.configureTestingModule({declarations: [PassesComponent, TestComponent]});
    TestBed.overrideComponent(TestComponent, { set: { template: html }});
  });

  it('should ...', () => {
    const fixture = TestBed.createComponent(TestComponent);
    fixture.detectChanges();
    expect(fixture.nativeElement.children[0].textContent).toContain('Passes Page!');
  });

});

@Component({selector: 'my-test', template: ''})
class TestComponent { }
