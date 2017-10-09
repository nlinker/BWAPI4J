/*
 * BridgeMap.cpp
 *
 *  Created on: Jul 16, 2017
 *      Author: imp
 */

#include "BridgeMap.h"
#include <BWAPI.h>

BridgeMap::BridgeMap() {

}

BridgeMap::~BridgeMap() {

}

using namespace BWAPI;

void BridgeMap::initialize(JNIEnv * env, jclass jc, jobject bwObject, jclass bwMapClass) {

	// read map information
		printf("reading map information...\n");
		jfieldID bwMapField = env->GetFieldID(jc, "bwMap", "Lorg/openbw/bwapi4j/BWMap;");
		jobject bwMap = env->GetObjectField(bwObject, bwMapField);

		// set mapHash
		jfieldID mapHashField = env->GetFieldID(bwMapClass, "mapHash", "Ljava/lang/String;");
		jstring mapHash = env->NewStringUTF(Broodwar->mapHash().c_str());
		env->SetObjectField(bwMap, mapHashField, mapHash);

		//set mapFileName
		jfieldID mapFileNameField = env->GetFieldID(bwMapClass, "mapFileName", "Ljava/lang/String;");
		jstring mapFileName = env->NewStringUTF(Broodwar->mapFileName().c_str());
		env->SetObjectField(bwMap, mapFileNameField, mapFileName);

		// set width
		jfieldID mapWidthField = env->GetFieldID(bwMapClass, "width", "I");
		env->SetIntField(bwMap, mapWidthField, (jint)Broodwar->mapWidth());

		// set height
		jfieldID mapHeightField = env->GetFieldID(bwMapClass, "height", "I");
		env->SetIntField(bwMap, mapHeightField, (jint)Broodwar->mapHeight());

		// set groundInfo (tile resolution)
		jfieldID groundInfoField = env->GetFieldID(bwMapClass, "groundInfo", "[[I");
		jobject* groundInfo2D = new jobject[Broodwar->mapWidth()];
		jobjectArray groundInfo2DArray = env->NewObjectArray(Broodwar->mapWidth(), env->GetObjectClass(env->NewIntArray(Broodwar->mapHeight())), 0);
		for (int i = 0; i < Broodwar->mapWidth(); ++i) {

			jint* groundInfo = new jint[Broodwar->mapHeight()];
			for (int j = 0; j < Broodwar->mapHeight(); ++j) {
				groundInfo[j] = Broodwar->getGroundHeight(i, j);
			}
			jintArray groundInfoArray = env->NewIntArray(Broodwar->mapHeight());
			env->SetIntArrayRegion(groundInfoArray, 0, Broodwar->mapHeight(), groundInfo);
			env->SetObjectArrayElement(groundInfo2DArray, i, groundInfoArray);
		}
		env->SetObjectField(bwMap, groundInfoField, groundInfo2DArray);
		printf("---debug---\n");
		// set walkabilityInfo (mini-tile resolution)
		jfieldID walkabilityInfoField = env->GetFieldID(bwMapClass, "walkabilityInfo", "[[I");
		jobject* walkabilityInfo2D = new jobject[Broodwar->mapWidth() * 4];
		jobjectArray walkabilityInfo2DArray = env->NewObjectArray(Broodwar->mapWidth() * 4, env->GetObjectClass(env->NewIntArray(Broodwar->mapHeight() * 4)), 0);
		for (int i = 0; i < Broodwar->mapWidth(); ++i) {

			jint* walkabilityInfo = new jint[Broodwar->mapHeight() * 4];
			for (int j = 0; j < Broodwar->mapHeight() * 4; ++j) {
				walkabilityInfo[j] = Broodwar->isWalkable(i, j) ? 1 : 0;
			}
			jintArray walkabilityInfoArray = env->NewIntArray(Broodwar->mapHeight() * 4);
			env->SetIntArrayRegion(walkabilityInfoArray, 0, Broodwar->mapHeight(), walkabilityInfo);
			env->SetObjectArrayElement(walkabilityInfo2DArray, i, walkabilityInfoArray);
		}
		env->SetObjectField(bwMap, walkabilityInfoField, walkabilityInfo2DArray);

		// set starting locations
		jobject startLocationsList = env->GetObjectField(bwMap, env->GetFieldID(bwMapClass, "startLocations", "Ljava/util/ArrayList;"));
		for (TilePosition tilePosition : Broodwar->getStartLocations()) {
			jobject startLocation = env->NewObject(tilePositionClass, tilePositionNew, tilePosition.x, tilePosition.y);
			env->CallObjectMethod(startLocationsList, arrayListAdd, startLocation);
		}

		if (env->ExceptionOccurred()) {
			env->ExceptionDescribe();
			return;
		}
		printf("done.\n");
}
