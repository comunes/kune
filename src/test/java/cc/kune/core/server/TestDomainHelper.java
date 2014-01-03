/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server;

import java.util.TimeZone;

import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.domain.AccessLists;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.Revision;
import cc.kune.domain.SocialNetwork;
import cc.kune.domain.User;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDomainHelper.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class TestDomainHelper {

  /**
   * Creates the access lists.
   * 
   * @param groupAdmin
   *          the group admin
   * @param groupEditor
   *          the group editor
   * @param groupViewer
   *          the group viewer
   * @return the access lists
   */
  public static AccessLists createAccessLists(final Group groupAdmin, final Group groupEditor,
      final Group groupViewer) {

    final AccessLists lists = new AccessLists();
    if (groupAdmin != null) {
      lists.addAdmin(groupAdmin);
    }
    if (groupEditor != null) {
      lists.addEditor(groupEditor);
    }
    if (groupViewer != null) {
      lists.addViewer(groupViewer);
    }
    return lists;
  }

  /**
   * Creates the big text.
   * 
   * @return the string
   */
  public static String createBigText() {
    final String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Fusce leo felis, cursus eget, scelerisque adipiscing, fringilla vel, sem. Vestibulum semper tristique sem. Etiam quam neque, consectetuer at, fermentum id, vulputate non, leo. Ut condimentum, mauris et sollicitudin faucibus, lectus arcu facilisis mi, eu pretium arcu tortor quis sem. Pellentesque sit amet nulla ut tellus dapibus blandit. Donec eu dolor vitae mi scelerisque pretium. Donec sit amet nunc ut risus laoreet porta. Integer dictum mi at mauris. Vivamus vulputate, dolor quis pharetra interdum, augue nisi congue eros, a consectetuer libero mi ut quam. Fusce commodo sem blandit massa. Phasellus vehicula varius felis.\n"
        + "\n"
        + "Aenean tempus. Ut vel elit a nisl adipiscing commodo. Suspendisse nibh. Praesent pellentesque. Curabitur fringilla tempor justo. Suspendisse bibendum faucibus ipsum. Aenean porta elementum pede. Sed vel odio et metus egestas ultrices. Nulla facilisi. Sed blandit fermentum purus. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam adipiscing. Suspendisse dapibus, est porta vehicula auctor, diam orci scelerisque risus, eu condimentum enim pede ut turpis. Nullam turpis. Aliquam erat volutpat. Integer vitae magna. Mauris sit amet turpis.\n"
        + "\n"
        + "Suspendisse potenti. Proin ac felis nec justo bibendum tempus. Donec at nunc et leo aliquet vestibulum. Mauris a justo nec orci ultrices placerat. Etiam tempus commodo dolor. Proin at justo blandit mauris vestibulum hendrerit. Donec mollis pede nec orci. Donec mauris. Quisque adipiscing facilisis ante. Aliquam elit quam, vulputate sit amet, posuere a, suscipit nec, nunc. Etiam vehicula ullamcorper metus. Mauris nec diam a arcu porta lobortis.\n"
        + "\n"
        + "Cras dapibus justo sed lacus. Ut ultrices, massa malesuada vestibulum rhoncus, eros sem fringilla sem, vel ultricies eros arcu vel augue. Ut vehicula nibh at lacus. Integer interdum turpis vitae arcu tempus luctus. Praesent a ligula. Donec viverra vestibulum est. Donec ultricies blandit mi. Morbi eleifend, nisi quis tristique pretium, lacus enim luctus nisi, quis lacinia ligula quam a orci. Praesent vel lectus. Pellentesque molestie ante sed leo. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vestibulum ultrices. Etiam augue. Morbi non purus. Nam libero ligula, lacinia at, rhoncus tincidunt, porttitor quis, leo.\n"
        + "\n"
        + "Nulla orci purus, laoreet vel, facilisis ac, rhoncus eu, dui. Phasellus sit amet urna quis dui tempor elementum. Curabitur at magna. In hac habitasse platea dictumst. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Nullam in dui. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum lacinia lectus eleifend mauris. Proin pellentesque tellus sit amet eros. Nulla purus mi, aliquam ac, faucibus a, varius viverra, enim. Vestibulum porttitor, arcu in molestie tristique, tellus velit imperdiet risus, dignissim sollicitudin pede ante sed orci. Nunc in elit. Aliquam adipiscing turpis condimentum tortor. Quisque tellus dolor, tincidunt eu, vehicula rutrum, tristique a, ipsum. Fusce a nunc at ligula egestas sodales. Maecenas molestie, arcu ac ullamcorper ornare, sem nunc sollicitudin velit, id tempor est dui vel elit. Praesent quam quam, vehicula a, mollis et, tincidunt a, nisi. Aliquam justo nibh, faucibus porta, convallis eu, sagittis eget, leo. Praesent at nisi. Nullam scelerisque.\n"
        + "\n"
        + "Nunc felis. Vestibulum imperdiet sem. Proin posuere, mauris sit amet molestie pharetra, purus turpis vulputate purus, et blandit ante urna non est. Morbi ligula turpis, faucibus fermentum, auctor a, euismod sit amet, lectus. Cras lacinia, purus et rutrum suscipit, pede odio facilisis dolor, nonummy commodo nunc sapien eu lectus. Proin egestas. Nam sodales aliquam eros. Ut lorem est, porttitor vel, dapibus a, tristique nec, diam. Ut non nulla eu nisi rhoncus lacinia. Ut ornare, sem non molestie condimentum, nisi risus iaculis sem, ac aliquet dui sem vel augue. Nullam varius pellentesque sem. Nulla facilisi.\n"
        + "\n"
        + "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam fringilla erat at diam. Suspendisse tincidunt. Nullam pharetra, nibh eget varius tempus, arcu massa dignissim justo, et scelerisque nisi velit commodo orci. Morbi sed nisi eu mi ultrices interdum. Cras in urna a elit sollicitudin consequat. Maecenas vel diam vitae dolor aliquam faucibus. Maecenas mattis, sem a iaculis auctor, est felis sagittis sapien, ut imperdiet diam nisi a nisl. Vestibulum rutrum nisl eget ante. Nulla non urna. In tellus urna, dignissim a, auctor in, pharetra eget, massa. Nam a magna. Ut id elit sit amet risus auctor laoreet. Quisque et urna. Praesent vehicula, nibh ac euismod rhoncus, ligula diam pharetra eros, ac sollicitudin elit odio a nunc. Quisque augue. Nulla pellentesque rhoncus leo. Proin sagittis erat.\n"
        + "\n"
        + "Donec fringilla. Mauris ultricies gravida pede. Nullam non urna et quam dignissim egestas. Mauris in nulla. Duis ac ligula non nulla sodales hendrerit. Aliquam condimentum lectus ac turpis. Etiam venenatis lacus vel sem. Proin convallis scelerisque diam. Suspendisse sed lacus a felis pretium rhoncus. Mauris at tortor ac libero euismod porttitor. Maecenas euismod. Etiam id orci. Donec consectetuer gravida lacus. Vivamus bibendum nisi ut felis. Phasellus ac nisl. Praesent lorem sapien, ultricies eget, accumsan a, viverra ut, magna. Integer lobortis leo vitae nunc. Sed vulputate justo eu sem. Proin et augue.\n"
        + "\n"
        + "Suspendisse pellentesque suscipit lectus. Fusce ut odio a turpis eleifend viverra. Vestibulum nisl. Aliquam sed turpis at ante pulvinar cursus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec sodales odio sagittis lorem. Duis mattis gravida odio. Cras mattis iaculis risus. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed iaculis laoreet elit. Cras ut tortor. Suspendisse eget nisi. Suspendisse ligula tellus, scelerisque a, consectetuer quis, tristique vel, arcu. Morbi varius, ligula et hendrerit iaculis, lorem purus luctus ipsum, eu interdum urna lorem id pede. Mauris est. In posuere dolor ac ipsum. Curabitur dictum. Vestibulum imperdiet. Pellentesque imperdiet aliquet dolor. Sed vulputate, enim at pulvinar sodales, felis libero malesuada felis, in iaculis odio mauris id neque.\n"
        + "\n"
        + "Pellentesque adipiscing. Nam ac neque sagittis nunc posuere fermentum. Sed quis orci vel enim commodo scelerisque. Ut aliquam. Quisque vitae tortor. Morbi libero augue, interdum et, nonummy ac, mattis vel, nisl. Suspendisse elit. Curabitur suscipit leo dignissim tellus. Proin felis massa, ultricies nec, fermentum sit amet, sollicitudin dignissim, mi. Maecenas ipsum ligula, tincidunt vitae, varius a, consequat ac, ligula. Suspendisse et urna. Phasellus consequat luctus arcu. Morbi molestie. Vivamus ligula. Mauris mollis.\n"
        + "\n"
        + "In nonummy iaculis leo. Sed vitae metus. Morbi vel eros in dolor accumsan pellentesque. In ut velit. Duis in tortor non enim ultricies scelerisque. Pellentesque ultrices dui a magna. In cursus pede non lorem. Vivamus nisl. Morbi ut tortor eu justo cursus dictum. Integer condimentum. Vivamus nisl risus, euismod id, molestie ut, feugiat nec, magna. Vivamus viverra sodales purus. Ut nunc. Vivamus dignissim sollicitudin nunc. Nulla tempor, diam ut viverra tincidunt, nisl orci sollicitudin lectus, eu dignissim tellus justo eu erat. Phasellus lobortis risus id libero. Ut faucibus diam ac justo. Duis neque magna, auctor eu, suscipit sit amet, tempor faucibus, lacus. Nunc mauris velit, feugiat sit amet, venenatis id, euismod ac, risus. Sed nisl lorem, eleifend et, scelerisque vel, imperdiet vitae, quam.\n"
        + "\n"
        + "Integer egestas dolor a purus. Ut quam dolor, elementum at, tincidunt venenatis, dapibus sollicitudin, ligula. Morbi luctus diam eget neque. Duis iaculis. Aliquam vel tellus auctor ipsum elementum hendrerit. In euismod ante tincidunt risus. Maecenas ac lorem sed magna volutpat gravida. Aliquam et sapien sed pede tristique vehicula. Cras et tellus. Nunc massa sem, aliquet a, sollicitudin vitae, malesuada vel, dolor. Suspendisse dui. Mauris purus. Maecenas egestas orci ut quam. Sed semper. Nullam feugiat. Ut aliquet, tellus quis aliquam viverra, orci velit viverra odio, vel adipiscing odio ligula in lorem. Sed ac sem vitae nibh pretium dapibus. Donec ac diam. Integer ultrices massa ut diam. Duis pretium posuere arcu.\n"
        + "\n"
        + "Morbi lorem. Fusce luctus lorem in risus gravida gravida. Donec vulputate volutpat metus. Pellentesque ac massa vitae orci placerat pellentesque. In hac habitasse platea dictumst. Curabitur pretium, ipsum in consequat tempor, ligula est volutpat nibh, eget porta diam massa ac leo. Curabitur placerat massa vel nibh. Nam at quam. In sagittis augue ac leo. Fusce adipiscing, ligula in auctor molestie, libero felis mattis sem, sit amet faucibus mauris nunc ac metus. Vivamus neque ipsum, bibendum sit amet, fringilla sit amet, bibendum ut, massa. Maecenas bibendum. Vivamus nunc est, accumsan et, tempus vitae, sagittis in, erat. Fusce turpis nunc, pretium vitae, varius sit amet, dignissim et, tortor. Quisque egestas, libero in dapibus tincidunt, justo arcu feugiat massa, sed sollicitudin lectus tellus sit amet augue. Vestibulum tristique nibh eget lorem. Curabitur cursus purus tristique nulla.\n"
        + "\n"
        + "Etiam blandit, arcu eu aliquam ornare, velit libero faucibus felis, sit amet sollicitudin velit leo in elit. Sed turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec eu risus. Sed risus lorem, luctus mattis, scelerisque eget, tincidunt a, enim. Vivamus consequat mollis tellus. Cras urna lorem, facilisis eu, porttitor mollis, dignissim posuere, ligula. Ut nonummy sapien. Praesent cursus. Ut turpis. Nulla enim. Aliquam aliquet, enim a gravida aliquam, pede odio porttitor nisi, non vestibulum odio erat a tortor. Donec fringilla ligula id augue. Curabitur vel tellus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur ullamcorper est vitae quam.\n"
        + "\n"
        + "Sed posuere neque. Vivamus tortor. Ut at mauris. Nam felis tellus, nonummy eu, gravida id, bibendum id, magna. Pellentesque laoreet mi sed felis. Fusce at lectus quis erat auctor laoreet. In interdum velit ut pede. Ut malesuada tincidunt velit. Suspendisse eu felis in mauris scelerisque iaculis. Morbi ornare dapibus risus. Quisque rhoncus elementum ante. Cras euismod nisi et lectus. Donec ut velit. Etiam a mauris. Integer cursus iaculis tellus. Fusce facilisis, nunc at semper tincidunt, dui purus accumsan nisi, in auctor magna ligula vel pede. Suspendisse ut arcu molestie lorem posuere vestibulum. Quisque pulvinar rhoncus ligula.\n"
        + "\n"
        + "Morbi sed enim. Donec in erat. Curabitur suscipit euismod lectus. Etiam dui nisi, volutpat vitae, tincidunt eget, tristique eget, turpis. Vivamus feugiat, quam ut tincidunt placerat, risus magna iaculis quam, id semper nisi tortor quis nibh. Donec a nulla. Aliquam turpis felis, aliquam non, ultrices eget, lacinia vel, nibh. Quisque vel nisi et eros vulputate eleifend. Aliquam nisl. Proin semper turpis. Cras ipsum.\n"
        + "\n"
        + "Donec scelerisque nunc non nibh. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nullam nec elit congue ligula vulputate pellentesque. Nunc ipsum. In imperdiet blandit lectus. Nulla ornare elit. Nunc at nibh at nibh semper vulputate. Cras scelerisque hendrerit erat. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum et mi. Etiam ut nisi et augue congue tempor. Curabitur nec libero non lacus congue suscipit. Nunc et metus a justo condimentum tincidunt.\n"
        + "\n"
        + "Phasellus ultrices placerat dui. Phasellus luctus, sem eu sagittis ultricies, massa dui eleifend libero, eu mollis urna dolor in neque. Duis pellentesque rutrum ipsum. Phasellus iaculis arcu et enim. Morbi neque. Sed felis velit, mattis sed, mollis quis, tempor sed, mi. Curabitur consequat, ipsum vel volutpat molestie, turpis dui suscipit urna, nec blandit libero pede quis leo. Sed ullamcorper luctus enim. Etiam rutrum imperdiet orci. Pellentesque pede. Nullam sollicitudin blandit quam. Pellentesque non nisi at justo venenatis pharetra. Praesent a dolor at lectus fringilla lobortis.\n"
        + "\n"
        + "Morbi eget dolor at mi tempus commodo. Ut accumsan convallis odio. Nam ac velit ac ligula consectetuer lacinia. Ut nibh tortor, auctor et, placerat et, rhoncus ac, ante. Nulla dignissim rhoncus quam. Vestibulum ac est. Integer pharetra, diam sed iaculis porta, turpis orci rhoncus risus, vel aliquet metus eros quis velit. Pellentesque cursus scelerisque ante. Suspendisse tempor, metus a vehicula laoreet, orci mauris tincidunt ante, vitae molestie lectus velit vel nulla. Suspendisse potenti. Morbi quis massa ac felis semper facilisis. Vivamus convallis, magna sed sollicitudin dapibus, tellus tortor congue erat, ac luctus risus mi sit amet lorem.\n"
        + "\n"
        + "In pellentesque libero ut lorem. Aliquam a libero eget nulla iaculis vehicula. Nullam turpis tellus, accumsan sit amet, bibendum ac, tincidunt in, libero. Mauris ac neque. Aliquam ultrices lorem id eros cursus pretium. Vivamus at elit ac felis tempor viverra. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nullam eget nunc. Morbi iaculis luctus massa. In turpis sapien, molestie pretium, vulputate in, facilisis sit amet, lacus. Proin tincidunt faucibus diam. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec nibh tellus, laoreet sit amet, fringilla vitae, ornare a, sapien. Praesent feugiat lobortis tellus. Nullam euismod euismod arcu. Proin a eros. Vivamus in orci. Proin nisi lacus, viverra ut, cursus lacinia, pharetra vitae, metus.\n"
        + "\n"
        + "Suspendisse porta risus eget lacus. Suspendisse posuere tortor non erat. Fusce fermentum. Vestibulum pulvinar, elit quis semper porta, nunc mauris dictum quam, eu adipiscing felis pede imperdiet tellus. Nulla sed eros. Sed vestibulum massa vitae purus. Aenean eu felis. Vivamus erat purus, pretium non, congue vulputate, lacinia vel, magna. Donec aliquet, lacus sit amet rhoncus lacinia, nisi tortor mollis felis, id mollis magna quam eu arcu. Morbi quis leo ac orci nonummy malesuada. Fusce iaculis tincidunt elit. Aenean vestibulum. Fusce ut diam eget lectus mattis dapibus. Nam suscipit, tellus vitae lacinia fermentum, nisi leo dignissim erat, a ultrices metus eros et enim. Curabitur congue hendrerit dui. Nulla a sem. Nam tincidunt faucibus leo.\n"
        + "\n"
        + "Aliquam quis ante. Sed sit amet eros nec mi aliquet mollis. Nunc libero quam, egestas et, cursus vel, congue id, metus. Etiam et lacus. Phasellus feugiat, mi vel ultrices feugiat, est risus sagittis pede, ut fringilla sem velit quis elit. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur et neque a diam sollicitudin egestas. Fusce fringilla egestas arcu. Integer congue risus quis libero. Suspendisse aliquam erat non leo. Ut tellus leo, accumsan quis, varius in, dapibus venenatis, dui. Cras mollis tincidunt nisl. Phasellus dignissim iaculis est. Morbi elit. Proin at nulla. Sed id arcu sit amet quam luctus volutpat.\n"
        + "\n"
        + "Vestibulum a lorem. Sed condimentum iaculis magna. Phasellus rhoncus. Vivamus malesuada, est et aliquam lobortis, dui massa vestibulum nunc, sed pellentesque mi velit ac orci. Nulla facilisi. Nunc a diam eu est iaculis lobortis. In velit sem, iaculis ut, ornare eu, eleifend at, arcu. Nulla quis metus vel neque rhoncus ultrices. Duis non ligula. Donec vitae augue et pede consectetuer egestas. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum eu lectus eget sem sagittis congue. Donec posuere lacus et turpis. Etiam sed dolor. Nam purus turpis, vehicula eu, suscipit a, vehicula vel, justo. Nunc eros neque, dignissim vel, consectetuer eu, malesuada sed, odio. Quisque consectetuer, risus vitae vulputate viverra, velit metus accumsan velit, vitae commodo sem sapien vitae lacus. Curabitur suscipit sem ut nisl. Aliquam nec metus. Donec vel pede a velit vestibulum luctus.\n"
        + "\n"
        + "Morbi neque turpis, tincidunt quis, dapibus et, sollicitudin sit amet, turpis. Praesent pretium pretium neque. Donec venenatis consequat nibh. Quisque eu nisi. Quisque ultricies vulputate turpis. Praesent non lacus et augue tempor dignissim. Praesent interdum, ipsum at viverra imperdiet, odio pede tincidunt augue, sit amet auctor metus arcu eget dui. Sed pellentesque vulputate nibh. Morbi eu lacus. Cras nonummy velit in nisl.\n"
        + "\n"
        + "Nullam mollis, dui id imperdiet semper, felis neque lacinia odio, id congue nisi mi a arcu. Maecenas quam. Vestibulum in sapien quis sapien pretium sodales. Maecenas laoreet mauris et turpis hendrerit gravida. Integer tristique, neque in accumsan auctor, dui purus laoreet enim, id laoreet arcu est elementum justo. Quisque adipiscing, metus id consequat elementum, dui magna bibendum ligula, et lacinia risus augue vitae ante. Nullam eget lacus. Vivamus viverra. Donec dolor nunc, nonummy vitae, consectetuer ac, pulvinar sit amet, urna. Pellentesque eget arcu non nulla malesuada condimentum. Morbi sapien felis, rhoncus vel, lobortis at, scelerisque sed, quam. Suspendisse a leo non velit volutpat rhoncus. Duis tempus magna ac nisl. Fusce augue. Sed volutpat posuere erat. Cras accumsan egestas nulla. Etiam mi massa, ultrices quis, facilisis ut, venenatis nec, odio. Integer felis. Morbi ut arcu non magna rutrum eleifend.\n"
        + "\n"
        + "Morbi rhoncus lorem sit amet nibh. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Donec suscipit aliquet tortor. Mauris vel erat. Sed commodo, nisl sed convallis scelerisque, metus turpis commodo nisl, ultricies blandit pede erat nec orci. Morbi turpis mauris, laoreet quis, iaculis vitae, faucibus et, sem. Praesent condimentum lorem in lorem. Aenean elit ipsum, congue in, pellentesque in, congue vel, odio. Fusce neque neque, auctor a, lobortis nec, tincidunt a, tortor. Sed ipsum purus, condimentum nec, dictum sit amet, adipiscing ac, neque. Pellentesque ligula. Maecenas nec mi. Integer iaculis tincidunt elit. Donec commodo orci ut risus. Nunc nonummy suscipit justo. Pellentesque augue urna, blandit sit amet, dapibus quis, mollis ac, pede. Suspendisse ornare velit et metus. Duis leo libero, euismod sed, mollis at, fringilla id, risus. Cras a justo vitae ante adipiscing molestie. Nam viverra.\n"
        + "\n"
        + "Nam semper pede id purus. Fusce ut nibh in enim fringilla placerat. Etiam sodales urna et eros. Nulla facilisi. Sed eleifend justo vel nibh. Praesent vestibulum feugiat neque. Nunc pretium. Etiam fringilla lorem eget sapien. Morbi id nisi. Etiam vehicula. Donec nisi risus, malesuada at, consequat pellentesque, iaculis in, turpis. Etiam iaculis risus nec odio. Fusce a risus. Curabitur dui lacus, ultricies quis, pretium eget, venenatis eget, tortor. Sed et urna. Duis enim ipsum, consequat sed, aliquet in, semper ornare, nibh. Aliquam consequat erat eget leo. Nulla facilisi. Vestibulum convallis. Fusce suscipit varius est.\n"
        + "\n"
        + "Duis magna. Nunc adipiscing euismod orci. Integer egestas, nisl non suscipit viverra, sapien mauris tempus quam, ac hendrerit ligula nulla quis nisi. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nunc eget pede ultrices elit aliquam tincidunt. Praesent nonummy. Maecenas faucibus accumsan nibh. Morbi dapibus erat. Duis feugiat, nibh nec iaculis consequat, nunc eros placerat libero, a commodo enim odio sit amet magna. Vivamus ultricies tempor lectus. Proin eros elit, aliquam ac, pharetra eget, congue nec, lorem. Suspendisse nisl lectus, gravida at, lacinia a, consequat sit amet, dolor.\n"
        + "\n"
        + "Sed vitae est eget eros vehicula cursus. Sed interdum orci sed orci. Ut tempus tincidunt magna. Aliquam erat volutpat. Suspendisse sed diam non mi eleifend euismod. Donec sem nunc, gravida sed, condimentum eget, tristique ac, odio. Maecenas mollis tellus eu risus. Donec tristique magna ut purus. Fusce et felis mattis turpis condimentum dignissim. Mauris sapien massa, lacinia tincidunt, vehicula in, scelerisque eget, nisl. Aliquam sed turpis interdum ligula molestie vestibulum. Nunc dolor. Sed sed tellus ac arcu egestas dapibus. Nunc magna. Curabitur non mauris. Integer quis ligula.\n"
        + "\n"
        + "Nulla facilisi. Vivamus at lectus in ligula fringilla posuere. Sed et metus. Curabitur sollicitudin. Praesent mi. Donec eros mi, auctor eget, accumsan vel, volutpat porta, metus. Nam molestie, nisi quis bibendum auctor, nisi risus suscipit sem, sed suscipit dolor nibh quis ante. Morbi at eros. Suspendisse rhoncus tellus at nulla. Mauris mollis orci sit amet turpis. Pellentesque tempor purus. Phasellus ultricies dictum massa. Proin nisl. Quisque eleifend dolor sit amet metus. Nulla pellentesque erat at leo dignissim suscipit. Fusce facilisis viverra quam. Duis viverra. Morbi vel eros. Curabitur nunc. Praesent id eros.\n"
        + "\n"
        + "Etiam at arcu ac pede lacinia pellentesque. Nullam urna magna, feugiat at, commodo nec, sollicitudin ut, diam. Nunc in urna. Phasellus congue ultricies ante. Etiam vel leo id eros tempor euismod. Vivamus sed justo. Mauris magna nulla, pretium sed, rhoncus elementum, varius in, enim. Proin sagittis nibh non mauris. Duis non pede non est interdum sagittis. Sed nulla.\n"
        + "\n"
        + "Integer id quam. Suspendisse ornare tortor sed ligula. Proin ac magna. Etiam dapibus ornare dui. Nullam ac felis quis lectus fermentum aliquet. Nulla sed augue et velit sollicitudin porta. Integer porttitor quam sit amet lacus. In dictum quam in nulla. Fusce euismod purus et justo. Nam sit amet mi quis orci rutrum rhoncus. Curabitur eleifend malesuada ligula. Nunc luctus auctor tellus. Phasellus pretium bibendum libero.\n"
        + "\n"
        + "Duis cursus, nibh quis tincidunt egestas, massa pede bibendum metus, a molestie sapien magna ac elit. Donec nunc nunc, vulputate et, sagittis in, bibendum vel, magna. Maecenas eleifend. Maecenas augue nunc, dignissim at, rhoncus at, pulvinar ut, eros. Aliquam dictum rutrum nisi. In hac habitasse platea dictumst. Aliquam justo mauris, vestibulum sed, cursus ut, suscipit ut, metus. Fusce vitae erat nec nisl posuere ultrices. Vestibulum non orci. Cras nunc lectus, aliquet a, pellentesque id, dapibus ut, felis. Sed lacinia tellus ac nunc. Vivamus massa diam, malesuada nec, convallis eget, lobortis id, nulla. Morbi bibendum, diam eu lacinia aliquet, enim risus suscipit sapien, at aliquet ligula lorem quis metus. Vestibulum euismod, lectus ac egestas facilisis, tortor dui pharetra diam, a aliquet enim ante quis neque. Praesent rhoncus ipsum non sem scelerisque vestibulum. Nullam posuere vestibulum tortor. Nulla facilisi. Aenean at urna sed odio posuere imperdiet. In semper suscipit nisl.\n"
        + "\n"
        + "Aliquam quam. Nullam euismod scelerisque arcu. Sed ultricies diam vitae neque. Nunc dictum. Curabitur eget tellus. Sed posuere venenatis nisl. Nulla condimentum, erat quis volutpat rutrum, pede sem mattis nisl, sit amet lacinia orci felis non nisi. Ut et turpis. Duis condimentum, eros ac rutrum pellentesque, enim tortor posuere felis, eget tempus sapien sapien id leo. Sed cursus, est sed tempus dapibus, turpis sapien tempor purus, eget rhoncus arcu diam non neque. Cras lorem. Donec posuere posuere orci. Sed nec metus id orci fringilla dictum. Curabitur ultrices viverra libero.\n"
        + "\n"
        + "Mauris tincidunt risus a libero. Suspendisse in nisl. Mauris id mauris. Mauris justo neque, varius tincidunt, hendrerit porttitor, ornare et, leo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Pellentesque adipiscing sapien ac sem. Fusce leo. Curabitur sit amet mauris. Suspendisse justo augue, vehicula sit amet, tincidunt id, semper vel, nisi. Curabitur tristique. Nulla facilisis. Fusce posuere, ante a vestibulum mattis, metus lectus pharetra tortor, rutrum egestas pede dui ut ligula. Aliquam lacus ligula, blandit id, aliquam at, elementum sed, felis. Nam laoreet. Vivamus a sapien. Cras quis neque. Sed est.\n"
        + "\n"
        + "Vestibulum id diam sit amet eros nonummy fringilla. Nullam cursus lobortis velit. In pharetra diam non augue. Duis convallis risus interdum mauris. Etiam rhoncus metus eget pede. Sed velit enim, rutrum in, tincidunt vel, pellentesque ac, elit. Vestibulum pretium mauris at magna. Suspendisse volutpat. Maecenas sit amet arcu. Fusce blandit. Mauris eget neque. Nunc semper nunc a libero. Donec enim dolor, vulputate ac, tempor sit amet, tincidunt eu, enim. Cras sagittis.\n"
        + "\n"
        + "Nullam commodo libero ut nunc tincidunt aliquet. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Etiam id tortor quis ipsum pretium blandit. Ut hendrerit orci ac arcu. In id leo. Suspendisse sit amet leo ut ipsum tristique venenatis. Sed in justo. Quisque feugiat. Morbi gravida arcu a orci. Maecenas in justo. Donec sodales, tortor id luctus interdum, ante lorem pretium ante, ac porttitor odio pede vitae purus. Sed iaculis pellentesque purus. Curabitur sit amet libero. Nam a metus ut tortor commodo rhoncus. Nulla suscipit. Mauris ac nibh. Suspendisse ante. Sed nonummy pede at eros. Aenean augue dui, nonummy id, ullamcorper non, dignissim non, diam. Pellentesque diam nisi, pulvinar et, mollis vel, egestas non, nibh.\n"
        + "\n"
        + "Phasellus tincidunt. Aliquam molestie. In tristique viverra magna. Integer consequat tempus urna. Aenean turpis orci, aliquet sit amet, mattis vitae, hendrerit eu, justo. Duis pharetra ultricies enim. Nulla facilisi. Nullam sodales, justo vel consectetuer tempor, risus est accumsan orci, non feugiat est dolor ut risus. In pharetra nunc nec urna. Donec diam sem, gravida et, pulvinar id, elementum eu, enim. Morbi porttitor. Integer quis nibh. Nulla ac nulla. Morbi massa orci, tincidunt in, porttitor a, rutrum id, magna. Phasellus vel quam. Curabitur pharetra rutrum quam. Praesent vestibulum, orci in pulvinar congue, diam justo iaculis eros, imperdiet tincidunt lorem urna quis eros. Praesent in eros at velit porttitor pretium.\n"
        + "\n"
        + "Fusce consectetuer mattis est. Sed non nulla. Ut auctor nisl. Pellentesque ac massa sit amet augue volutpat molestie. Aliquam in purus. Etiam vitae libero. Nunc sapien lacus, mollis ac, auctor eu, fermentum eu, ligula. Suspendisse potenti. Duis molestie pulvinar elit. Vestibulum tellus libero, ultrices vitae, blandit eget, porttitor ac, odio.\n"
        + "\n"
        + "Vivamus malesuada sapien sed ipsum. Aenean quis nisl quis orci suscipit placerat. Pellentesque porttitor commodo nulla. Proin quis libero. Donec congue porttitor sapien. Phasellus sed est vel ligula vulputate facilisis. Mauris sit amet diam. In quis neque id orci tempus vehicula. Suspendisse egestas feugiat neque. Ut metus leo, placerat eget, pellentesque sed, porta at, nisi. Suspendisse potenti. Phasellus ultricies justo sit amet sem. Aenean porttitor enim nec nibh. Integer sed orci.\n"
        + "\n"
        + "Sed dignissim, nisi eu convallis luctus, enim justo posuere mauris, quis cursus leo tortor vitae metus. Nullam nec enim a nisl aliquet aliquam. Morbi nunc nisl, sodales et, condimentum in, sagittis vel, pede. Vivamus a sem. Praesent imperdiet luctus elit. Nulla facilisi. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed ac mauris non sapien pretium consequat. Fusce ut purus. Praesent sem. Proin placerat posuere lorem. Donec vel libero. Morbi tincidunt sapien at nisl. Sed porta felis eu pede. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam scelerisque. Aenean sit amet metus. Morbi accumsan diam et neque mattis vestibulum. Suspendisse sollicitudin odio et odio. Pellentesque auctor ante non elit.\n"
        + "\n"
        + "Morbi eget quam eu orci lobortis blandit. Duis consequat nisi ut magna. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Nam posuere, diam at pretium auctor, tortor orci mattis mi, quis bibendum leo diam sed tellus. Vestibulum rutrum nunc. Donec sapien mauris, vehicula ac, commodo ac, hendrerit ut, risus. Quisque non magna at sapien vulputate nonummy. Proin pede enim, tempus sit amet, porttitor commodo, adipiscing non, est. Nulla facilisi. Aenean condimentum ante vitae pede. Donec id tortor quis tellus dictum dictum. Sed pretium ipsum nec pede. Nunc porttitor. In auctor quam at justo. Nunc ligula arcu, bibendum ac, volutpat sed, hendrerit eu, augue. Integer molestie pede eget ipsum.\n"
        + "\n"
        + "Nulla faucibus urna quis purus. Sed lacinia. Phasellus sollicitudin augue quis eros. Aenean vel mauris non ipsum molestie scelerisque. Cras pharetra, ligula vel adipiscing fringilla, nunc odio nonummy sapien, nec imperdiet sem pede nec mi. Ut vehicula enim a nunc. Donec suscipit. Curabitur dapibus nisi. Aenean nunc sapien, tristique dapibus, placerat sed, venenatis sed, enim. Etiam bibendum laoreet ligula. Aliquam mauris tortor, feugiat non, consectetuer sit amet, accumsan nec, quam. Nunc quis dui. Nulla facilisi. Cras at nisl. Fusce orci. In turpis. Aliquam nisi est, pellentesque nec, rutrum vitae, commodo at, nisi.\n"
        + "\n"
        + "Pellentesque ligula massa, faucibus at, porta ac, cursus vitae, turpis. Nam nibh nisi, viverra sit amet, porttitor vel, molestie quis, arcu. Nullam consectetuer egestas lacus. Suspendisse potenti. Nam scelerisque eleifend nunc. Suspendisse potenti. Maecenas turpis. Nam eleifend semper augue. Praesent ante nisl, sagittis sed, rhoncus eget, scelerisque eu, felis. Nullam non urna. Vestibulum eget ante. Sed id quam. Morbi ut lacus venenatis dolor lacinia commodo. Sed tincidunt libero at elit. Vivamus vitae tortor. Suspendisse eget nisl.\n"
        + "\n"
        + "Suspendisse dignissim luctus nisi. Pellentesque varius. Sed gravida aliquet lorem. Phasellus ligula mi, rhoncus id, cursus et, consequat quis, ante. Etiam placerat posuere enim. Donec sed tortor eget pede gravida tristique. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Maecenas libero. Nunc a metus nec urna mollis dignissim. Cras sodales sapien ac leo. Mauris suscipit. Mauris at nisl sit amet turpis porta semper. Nullam non elit. Fusce pharetra vestibulum odio. Sed a eros id neque accumsan congue. Fusce turpis mi, volutpat a, convallis non, auctor luctus, tortor. Sed blandit, dui at egestas lobortis, ante sapien dictum quam, vel semper arcu nisi id est.\n"
        + "\n"
        + "Suspendisse posuere. Etiam gravida scelerisque enim. Praesent placerat. Nam ligula nunc, faucibus quis, porttitor in, molestie id, velit. Cras sed purus. Nullam in erat. Suspendisse nonummy aliquam mi. Etiam eu lorem. Fusce rhoncus mi ac lectus. Proin sed augue. Donec eu libero. Vestibulum iaculis ultrices diam. Nam tincidunt tincidunt risus. Suspendisse non ipsum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam congue erat ac nunc. Nulla interdum nisi vestibulum quam. Curabitur turpis. Praesent varius, nunc ac lacinia egestas, ante elit dictum pede, eu consequat risus ligula id lacus. Donec vel arcu ac augue feugiat posuere.\n"
        + "\n"
        + "Etiam tincidunt pulvinar ipsum. In sit amet nunc volutpat lorem luctus feugiat. Proin augue purus, dictum eu, posuere non, vestibulum vitae, libero. Vivamus fermentum. Sed lorem. Aliquam egestas dapibus eros. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Vestibulum nec turpis non lacus feugiat auctor. Sed rhoncus libero eu eros pretium dictum. Duis sed quam. Etiam nec nulla. Morbi elit metus, pulvinar quis, condimentum tristique, egestas eu, lorem. Nam turpis. Sed tincidunt, justo varius nonummy vestibulum, ante odio ultricies diam, sed ullamcorper pede turpis eu elit. Integer sit amet arcu. Sed lorem. Quisque vehicula erat vitae sem. Nunc consectetuer tellus sed neque. Nullam fringilla.\n"
        + "\n"
        + "Etiam tempus bibendum sem. Morbi nunc orci, molestie at, pretium non, blandit vitae, justo. Suspendisse potenti. Nulla mollis, metus id interdum malesuada, dolor nulla iaculis ligula, nec lacinia nibh odio volutpat arcu. In hac habitasse platea dictumst. Nullam lorem turpis, mollis a, placerat sit amet, lobortis varius, lectus. Nunc viverra vulputate arcu. Donec nec urna. Vestibulum dapibus, metus ut molestie mollis, elit diam tempor est, eu dictum lectus est in velit. Vivamus faucibus, elit id pellentesque malesuada, purus libero porttitor turpis, vel gravida nunc ligula tempor mauris. Vestibulum suscipit lobortis libero. Vestibulum interdum, sem eget eleifend adipiscing, libero lectus aliquam odio, sed bibendum ante dui ut mi. Pellentesque scelerisque, lorem sed pulvinar eleifend, augue velit lacinia dui, nec semper nibh erat sit amet eros. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent fermentum arcu eget leo. Curabitur in felis. Curabitur sit amet massa in est cursus ultricies. Sed neque dolor, mollis vel, pulvinar ac, fermentum eget, mi.\n"
        + "\n"
        + "Nulla facilisi. Maecenas eros arcu, gravida vitae, commodo ac, commodo vel, mi. Suspendisse non metus. Aenean aliquet condimentum dui. Donec augue turpis, adipiscing sed, ultricies in, condimentum nec, metus. Nam sit amet pede. Morbi fringilla. Sed sollicitudin massa nec nisi. Nam ut libero. Morbi malesuada, lacus at bibendum luctus, ante diam tincidunt erat, id consectetuer eros felis ut nisi. Suspendisse ullamcorper rutrum tellus. Aenean in metus sit amet magna tempus pellentesque.\n"
        + "\n"
        + "Nullam venenatis tellus id pede. Nunc ac nulla a turpis bibendum laoreet. Quisque eget diam eget arcu scelerisque ultricies. Nullam faucibus accumsan ipsum. Quisque diam nisi, euismod nec, cursus at, lobortis sit amet, nunc. Quisque fermentum sapien eget nibh. Curabitur scelerisque. In hac habitasse platea dictumst. Aliquam diam dui, pharetra quis, ultrices ac, rutrum ut, mi. Praesent egestas. Cras convallis sem ac mauris. Curabitur ultrices odio pretium mi. Nam mi metus, adipiscing id, laoreet id, mattis nec, tortor. Ut sed turpis varius purus vehicula rutrum. Duis suscipit erat eget lectus. Donec feugiat varius risus. Donec facilisis dolor ut justo.\n"
        + "\n"
        + "Curabitur lectus nibh, congue ac, laoreet sed, vulputate ac, ipsum. Donec convallis nisl placerat augue. Proin nec felis in arcu tincidunt facilisis. Duis congue tincidunt sapien. Vivamus lobortis purus eget risus. Aenean aliquet, ipsum quis bibendum eleifend, justo dolor pellentesque justo, in elementum orci enim congue nisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed sem enim, lobortis nec, interdum vitae, sollicitudin non, dolor. Sed commodo euismod orci. Maecenas vel arcu et nunc dignissim volutpat. Morbi molestie aliquam mi. Sed id neque rutrum nunc sagittis ultricies. Mauris diam magna, ullamcorper nec, tincidunt id, laoreet nec, dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Aliquam feugiat nisl non arcu. Nulla facilisi. In hac habitasse platea dictumst. Quisque metus.\n"
        + "\n"
        + "Suspendisse potenti. Cras malesuada pulvinar est. Ut nisi. Curabitur hendrerit, mauris vel laoreet dictum, quam dolor elementum orci, venenatis gravida sem erat malesuada ante. Aliquam condimentum. Suspendisse orci quam, varius in, egestas ut, mollis quis, arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus sed mauris at massa interdum vulputate. Morbi rutrum ligula. Vivamus nec libero. Curabitur lacinia, lorem eget feugiat pretium, purus urna consequat leo, non bibendum dolor augue vitae quam. Proin mi nunc, feugiat nec, convallis ac, feugiat ac, mi. Praesent pulvinar. Cras cursus nisi id nulla. Integer vel magna. Cras felis. Nam id tellus ac velit interdum convallis. Cras dignissim tortor sed mi.\n"
        + "\n"
        + "Nunc vestibulum, tortor eu commodo facilisis, leo mi vehicula sapien, eget hendrerit ipsum odio vitae sem. Aliquam consequat augue eu ligula. Vivamus et neque. Sed sed arcu at justo placerat placerat. Donec posuere. Phasellus lacus ligula, pulvinar at, porta et, tincidunt quis, quam. Curabitur pede mauris, interdum laoreet, feugiat condimentum, mollis ut, lectus. Curabitur laoreet, quam a malesuada suscipit, tellus lorem aliquet diam, a feugiat lorem neque hendrerit risus. Etiam viverra orci vitae mauris. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Pellentesque tellus augue, aliquam non, porttitor non, ornare eu, mi. Nullam blandit sagittis ante. Aliquam tincidunt, lorem ac imperdiet sodales, elit felis cursus dolor, vel interdum leo purus at tellus. Nunc viverra, dui non sagittis dapibus, justo eros accumsan massa, eu hendrerit lorem nulla ac magna. In hac habitasse platea dictumst.\n"
        + "\n"
        + "Ut velit risus, malesuada eget, vestibulum nec, ornare ac, ipsum. Morbi volutpat aliquet turpis. Nam enim. Nulla semper est vitae neque. Phasellus non dui. In hac habitasse platea dictumst. Morbi auctor porttitor est. Nullam non nulla quis elit eleifend porttitor. Aliquam erat volutpat. Duis erat. Cras lobortis nisl in elit. Vivamus leo. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean ac neque. Nam sem nulla, ornare quis, dictum eget, fermentum quis, lorem. In blandit lacus. Aenean in enim.\n"
        + "\n"
        + "Donec sit amet orci id quam rhoncus egestas. Integer metus. Vestibulum risus lorem, posuere ac, aliquam in, mollis at, lorem. Integer metus. Aliquam blandit scelerisque orci. Nulla eu ligula. Nulla nec nisi et libero cursus bibendum. Suspendisse potenti. Maecenas in massa eget tortor consectetuer scelerisque. Morbi tincidunt velit. Nam lectus purus, malesuada ac, gravida nec, sodales sed, sem. Vestibulum feugiat. Cras viverra vestibulum massa. Mauris scelerisque. Fusce ut ante ut ante molestie molestie. Praesent non arcu. Fusce auctor blandit ipsum. Nullam est.\n"
        + "\n"
        + "In pulvinar arcu quis ligula. Duis volutpat metus at lorem. Aenean eu urna vitae velit tempus sollicitudin. Duis erat lectus, dignissim sit amet, fermentum at, mattis at, nisi. Nunc vel libero. Nulla eget risus et dui facilisis sagittis. Donec vel est. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Sed adipiscing massa nec dolor. Morbi a ligula a ante vulputate tempor. Nullam lorem lacus, sodales gravida, scelerisque in, laoreet eu, nulla. Nam in tortor non nunc consectetuer porta. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Morbi ut diam. Proin nonummy. Pellentesque erat justo, egestas ac, sagittis id, suscipit vitae, diam. Aenean tincidunt urna sit amet urna. Donec turpis. Mauris cursus dolor a massa.\n"
        + "\n"
        + "Phasellus sem arcu, semper vel, eleifend eu, viverra eu, nulla. Fusce felis elit, semper cursus, ornare et, eleifend vel, pede. In est tellus, vulputate sit amet, tristique sed, imperdiet vel, lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Etiam justo. Proin volutpat. Nullam vitae dui nec urna convallis euismod. Nulla facilisi. Phasellus porttitor. Aenean scelerisque dolor sed justo. In vel nisi gravida mi mollis tempus. Curabitur ante lorem, fringilla eu, laoreet vitae, ultricies id, nisi. Sed id ante. Morbi tristique, est id porta tempus, turpis ipsum porta elit, at gravida leo odio in ante. Fusce euismod.\n"
        + "\n"
        + "Maecenas urna quam, fringilla eu, porta eu, semper quis, dui. Nunc congue est eu ante. Donec suscipit viverra leo. Nullam tempus porttitor est. Cras eu elit eget erat consectetuer convallis. Nam tellus ligula, eleifend ultrices, consequat et, malesuada non, velit. Quisque vitae enim in elit accumsan ullamcorper. Pellentesque eget enim. Sed nonummy, orci pretium malesuada adipiscing, dolor velit bibendum pede, non tincidunt diam sapien ac nunc. Maecenas porta leo at augue.\n"
        + "\n"
        + "Aliquam erat volutpat. Curabitur eu arcu faucibus tellus hendrerit bibendum. Praesent orci pede, lacinia vitae, tristique vitae, suscipit non, turpis. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Suspendisse tincidunt laoreet sapien. Vestibulum varius neque in odio. Donec sem ipsum, egestas id, molestie sit amet, luctus eu, est. Integer leo eros, laoreet non, lacinia in, cursus nec, mi. Donec ornare velit non massa. Curabitur turpis ligula, pharetra lacinia, euismod eu, tincidunt ut, velit. Cras nec ipsum eget augue sagittis vestibulum. Cras mattis dapibus tortor. Pellentesque sit amet arcu eu quam pretium malesuada. Phasellus turpis nisi, mattis ac, gravida et, aliquam eget, tellus. Aenean quis justo eget erat congue tincidunt.\n"
        + "\n"
        + "Aenean non tortor ut sem facilisis blandit. Sed ante lectus, bibendum id, fermentum ac, vestibulum et, ipsum. Sed tincidunt. Ut tincidunt. In sit amet odio. Sed massa. Nullam auctor. Nam lacinia sem eu lorem. Nunc neque tortor, euismod ut, dictum vel, commodo vehicula, lorem. Nullam metus urna, interdum non, semper eget, sodales in, eros. Sed eget magna vel dui tincidunt iaculis. Nam faucibus justo eget nunc. Suspendisse potenti. Proin fermentum erat in orci. Suspendisse semper lorem. Fusce rhoncus nulla a sapien. Mauris turpis turpis, bibendum euismod, porttitor pretium, semper id, ante. Ut eget est vel elit tempus auctor. Morbi interdum quam. In hac habitasse platea dictumst.\n"
        + "\n"
        + "Praesent vel ante ac mi consectetuer accumsan. Quisque quis ipsum. Suspendisse vitae magna vitae metus congue tempus. Aenean sagittis, ante nec vehicula molestie, ligula nisl tristique justo, quis suscipit nulla urna in sapien. Pellentesque eget felis pellentesque orci dignissim interdum. Sed metus lorem, egestas quis, iaculis at, venenatis eleifend, dui. Praesent elementum porttitor lacus. Sed suscipit dui vel dolor. Sed sit amet nunc et nunc rutrum eleifend. Curabitur et ligula. Phasellus consectetuer mi ut pede. In a libero in urna blandit varius. Sed pellentesque ligula aliquet risus. Ut laoreet, eros volutpat rhoncus congue, erat felis porttitor augue, vel laoreet eros est vitae pede. Suspendisse sit amet elit. Quisque quis dui. Vivamus massa. Suspendisse quis felis. Aliquam diam enim, volutpat in, commodo pellentesque, consectetuer ut, nibh.\n"
        + "\n"
        + "Donec mattis. Quisque purus purus, mattis sit amet, sollicitudin at, faucibus quis, diam. Sed ut urna a arcu posuere porta. Duis feugiat massa ut pede. In hac habitasse platea dictumst. Donec lectus nisl, elementum vel, pulvinar a, bibendum at, lorem. Vestibulum non lacus. Praesent arcu. Maecenas justo. Proin vel ipsum. Etiam egestas arcu vitae nibh. Aenean vel lacus id lacus tempus hendrerit. Ut lobortis ornare nibh.\n"
        + "\n"
        + "Donec nonummy, massa a accumsan tristique, purus diam iaculis nibh, ut dictum lorem metus sit amet nibh. Aliquam id eros et diam congue condimentum. Vestibulum porttitor massa a purus dignissim fringilla. Curabitur lectus. Phasellus auctor tortor vitae orci. Duis ante est, hendrerit ut, commodo sit amet, scelerisque eget, arcu. Cras luctus, risus eu volutpat sagittis, sem nulla accumsan libero, nec volutpat eros dui sit amet ante. Nunc convallis purus sed nisl. Nulla rutrum. Etiam viverra, orci at facilisis pretium, odio libero sollicitudin eros, ac pharetra est diam eu orci. Cras dolor risus, porttitor eu, convallis sit amet, tincidunt a, nulla. Nunc porttitor luctus diam. Mauris erat magna, pharetra ac, pretium consequat, viverra ut, nisl. Donec blandit mollis metus. Praesent dui pede, ultricies non, pellentesque et, molestie eu, nulla.\n"
        + "\n"
        + "Vivamus eleifend, leo sit amet tristique elementum, urna urna sollicitudin dolor, eu egestas est enim et tellus. Maecenas congue feugiat dolor. Nullam semper. Aliquam ipsum justo, fringilla et, consequat in, malesuada id, arcu. Ut orci. Aliquam sagittis consequat urna. Nam elementum diam quis lacus. Morbi elementum adipiscing orci. Mauris eros neque, vehicula quis, egestas sit amet, imperdiet id, mi. Cras convallis nisi vitae nulla. Vestibulum at purus.\n"
        + "\n"
        + "Etiam quis nisi vel sapien commodo ultrices. Etiam lacus. Etiam venenatis elit nec purus. Fusce id est. Vivamus justo tellus, facilisis quis, bibendum non, lacinia eget, ante. Mauris aliquet. Suspendisse iaculis placerat purus. Aliquam erat volutpat. Quisque vel sem. Donec pulvinar, lorem id malesuada blandit, nibh diam lobortis mi, in elementum velit mi a felis. In egestas. Suspendisse pede.\n"
        + "\n"
        + "Curabitur eu urna eget pede placerat adipiscing. Sed adipiscing bibendum tortor. Integer justo. Fusce risus enim, hendrerit ac, pellentesque sed, egestas sed, orci. Nunc orci erat, rutrum eu, euismod quis, condimentum eget, metus. Cras tincidunt. Phasellus scelerisque. Nunc sed nulla. Aliquam et sapien ac purus faucibus tincidunt. Ut id ligula egestas enim gravida vulputate. Maecenas consequat. Etiam varius sapien id pede. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Integer cursus libero eget pede. In faucibus ultricies felis. Praesent nulla. Donec tortor.\n"
        + "\n"
        + "Nulla quam felis, semper pellentesque, imperdiet a, egestas ut, purus. Fusce vitae elit quis dolor hendrerit gravida. Donec vestibulum. Aenean fermentum semper libero. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla facilisi. Donec eleifend leo at sem. Vivamus consectetuer, magna et iaculis elementum, erat sapien viverra lacus, consectetuer consectetuer justo leo id nulla. Phasellus id nisl. Aliquam molestie pede sit amet nisl. Duis iaculis vehicula tellus.\n"
        + "\n"
        + "Donec quis ipsum eget lacus vehicula fringilla. Vivamus dui est, interdum sed, scelerisque at, rhoncus at, massa. Proin placerat sodales elit. Mauris sagittis urna et enim. Vestibulum aliquet convallis nisi. Donec auctor ullamcorper lectus. Nullam vitae purus facilisis justo condimentum malesuada. Donec dui lectus, iaculis quis, lobortis aliquam, auctor nec, lectus. Aenean vel mi. Pellentesque et arcu faucibus orci convallis ullamcorper. In consequat. Praesent lobortis faucibus leo. Duis ut justo. Morbi viverra aliquam dui. Fusce laoreet dolor porta quam. Duis molestie porttitor dui. Quisque libero sem, tempor vitae, rhoncus sed, suscipit non, ante.\n"
        + "\n"
        + "In accumsan. Ut metus turpis, tincidunt in, rutrum quis, ultrices feugiat, tellus. Vivamus pellentesque urna sit amet elit. Mauris iaculis dui nec massa. Duis ac sem. Suspendisse sed lorem. Etiam eget sapien. Duis adipiscing porttitor neque. Nullam nisl elit, imperdiet et, pretium et, pretium ac, velit. Aenean eu lectus. Nam suscipit blandit purus. Vestibulum vel pede sodales nisi pulvinar porta. Integer ut urna ac arcu imperdiet tempus. Etiam rutrum, quam id euismod mattis, ligula tortor imperdiet orci, nec fringilla tortor libero eu est. Nulla pretium ligula. Sed eu nisi. Proin neque lorem, suscipit euismod, vestibulum eu, pretium volutpat, elit. Aenean mauris lacus, dictum nec, vulputate et, aliquam et, mi. Praesent aliquam purus ac neque.\n"
        + "\n"
        + "Maecenas mollis risus eget turpis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Curabitur lacus urna, auctor in, commodo ut, blandit vitae, tellus. Nunc vitae elit. Mauris elementum arcu. Nulla facilisi. Nunc diam tellus, porttitor non, dictum id, fermentum eget, nulla. Nulla facilisis enim nec felis. Phasellus interdum, erat a fermentum mattis, urna nisl iaculis ipsum, sit amet venenatis ante massa sit amet enim. Quisque facilisis nibh. Donec sit amet mauris et nisi eleifend egestas. Suspendisse pellentesque malesuada dui. Ut vestibulum, tortor sit amet pellentesque venenatis, sem turpis scelerisque massa, ut facilisis est ipsum ut arcu. Suspendisse feugiat, enim sit amet vehicula varius, tortor justo dapibus odio, et semper magna turpis ac ligula.\n"
        + "\n"
        + "Pellentesque non diam. Donec vel neque vel turpis vulputate ornare. Sed porttitor mi id augue. Etiam sapien mi, lobortis sed, dapibus nec, congue at, augue. Vestibulum condimentum, leo in cursus porta, quam nisl eleifend lectus, quis mattis sapien nisl sed lacus. Morbi pellentesque viverra massa. Aenean volutpat nibh lobortis tortor. Aliquam id diam. Nulla facilisi. Etiam ipsum elit, vestibulum vitae, accumsan vitae, aliquam sit amet, odio. Donec condimentum. Phasellus sodales, nunc et ullamcorper laoreet, turpis arcu egestas pede, id pharetra ante tortor vel est. Maecenas sem. Nulla lorem massa, mollis id, malesuada eu, faucibus a, est.\n"
        + "\n"
        + "Curabitur molestie, nunc at aliquet tempor, risus metus adipiscing felis, vitae euismod justo felis id eros. Aenean tincidunt nunc sed eros. Nulla ac tellus. Aliquam erat volutpat. Nulla vitae felis. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In consectetuer posuere purus. Phasellus interdum. Ut laoreet semper nunc. Duis blandit quam a velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Curabitur aliquam dolor et tortor. Pellentesque vulputate. Pellentesque diam purus, facilisis nec, fermentum a, consequat quis, ipsum. Ut euismod dui ut massa. Proin nonummy nisi ut pede. Pellentesque sollicitudin lectus a purus.\n"
        + "\n"
        + "Cras vulputate. Praesent tempus fringilla diam. Suspendisse at elit. Cras ut nulla eget mi dapibus pretium. Etiam hendrerit tortor sed lectus. Cras mauris. Nam ligula. Mauris semper, arcu ut facilisis molestie, lorem sapien iaculis quam, at dignissim sapien mauris id pede. In metus ante, porttitor id, fringilla a, adipiscing eget, nisi. Ut ornare sapien nec velit. Aenean tortor metus, pellentesque vitae, iaculis eget, imperdiet nec, turpis. Vestibulum sit amet erat ac urna auctor imperdiet. Nulla facilisi. Quisque facilisis pede vel mi consequat dignissim. Vivamus sapien.\n";
    return text;
  }

  /**
   * Creates the descriptor.
   * 
   * @param id
   *          the id
   * @param title
   *          the title
   * @param content
   *          the content
   * @return the content
   */
  public static Content createDescriptor(final long id, final String title, final String content) {
    final Content descriptor = new Content();
    descriptor.setId(id);
    final Revision rev = new Revision(descriptor);
    descriptor.addRevision(rev);
    rev.setTitle(title);
    rev.setBody(content);
    return descriptor;
  }

  /**
   * Creates the folder with id.
   * 
   * @param id
   *          the id
   * @return the container
   */
  public static Container createFolderWithId(final long id) {
    final Container container = new Container();
    container.setId(id);
    return container;
  }

  /**
   * Creates the folder with id and group and tool.
   * 
   * @param id
   *          the id
   * @param groupShortName
   *          the group short name
   * @param toolName
   *          the tool name
   * @return the container
   */
  public static Container createFolderWithIdAndGroupAndTool(final int id, final String groupShortName,
      final String toolName) {
    final Container container = createFolderWithIdAndToolName(id, toolName);
    final Group owner = new Group();
    owner.setShortName(groupShortName);
    container.setOwner(owner);
    return container;

  }

  /**
   * Creates the folder with id and tool name.
   * 
   * @param id
   *          the id
   * @param toolName
   *          the tool name
   * @return the container
   */
  public static Container createFolderWithIdAndToolName(final int id, final String toolName) {
    final Container container = createFolderWithId(id);
    container.setToolName(toolName);
    return container;
  }

  /**
   * Creates the group.
   * 
   * @param number
   *          the number
   * @return the group
   */
  public static Group createGroup(final int number) {
    final Group group = new Group("ysei" + number, "Yellow Submarine Environmental Initiative" + number);
    group.setId(Long.valueOf(number));
    return group;
  }

  /**
   * Creates the social network.
   * 
   * @param groupInAdmins
   *          the group in admins
   * @param groupInCollab
   *          the group in collab
   * @param groupInViewer
   *          the group in viewer
   * @param groupInPendings
   *          the group in pendings
   * @return the social network
   */
  public static SocialNetwork createSocialNetwork(final Group groupInAdmins, final Group groupInCollab,
      final Group groupInViewer, final Group groupInPendings) {

    final SocialNetwork socialNetwork = new SocialNetwork();
    socialNetwork.addAdmin(groupInAdmins);
    socialNetwork.addCollaborator(groupInCollab);
    socialNetwork.addViewer(groupInViewer);
    socialNetwork.addPendingCollaborator(groupInPendings);
    return socialNetwork;
  }

  /**
   * Creates the user.
   * 
   * @param number
   *          the number
   * @return the user
   */
  public static User createUser(final int number) {
    final String shortName = "shortname" + number;
    final String longName = "name" + number;
    final User user = new User(shortName, longName, "email@domain" + number, "diggest".getBytes(),
        "salt".getBytes(), null, null, TimeZone.getDefault());
    final Group userGroup = new Group(shortName, longName);
    userGroup.setAdmissionType(AdmissionType.Closed);
    userGroup.setSocialNetwork(createSocialNetwork(userGroup, userGroup, userGroup, null));
    user.setUserGroup(userGroup);
    return user;
  }
}
