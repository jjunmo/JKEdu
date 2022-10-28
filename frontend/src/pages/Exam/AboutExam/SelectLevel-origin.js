import { Link } from "react-router-dom";
import { useState } from "react";
import styled from "styled-components";

const Main = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: calc(100% - 80px);
`;

const SelectLevelDiv = styled.div`
  width: 500px;
  height: 350px;
  margin: 20px auto 10px auto;
  vertical-align: middle;
  border: 5px solid #e9dac1;
  border-radius: 20px;
  font-family: jua;
`;

const BasicButton = styled.button`
  box-sizing: border-box;
  border: none;
  padding: 0px;
  width: 240px;
  height: 70px;
  margin-left: 5px;
  margin-bottom: 5px;
  font-family: Arial;
  font-size: 18px;
  border-radius: 10px;
  &:hover {
    background-color: #9ed2c6;
  }
`;
const IntermediateButton = styled(BasicButton)`
  margin-right: 5px;
  margin-left: 10px;
`;
const AdvancedButton = styled(BasicButton)``;
const PrepButton = styled(IntermediateButton)``;

const TestPageLink = styled(Link).attrs((prop) => ({}))`
  border: none;
  border-radius: 15px;
  font-family: jua;
  font-size: 15px;
  padding: 3px 30px 3px 30px;
  margin: 0px;
  text-decoration: none;
  color: black;
  background-color: ${(prop) =>
    prop.value === "" ? "rgb(233,233,233)" : "#97d2ec"};
  pointer-events: ${(prop) => (prop.value === "" ? "none" : "")};
  &:hover {
    cursor: pointer;
  }
`;

const SelectLevelorigin = () => {
  const [selectLevel, setSelectLevel] = useState("");

  const setLevel = (level) => {
    setSelectLevel(level);
  };

  return (
    <Main>
      <SelectLevelDiv>
        <h1 style={{ textAlign: "center" }}>레벨을 선택하세요.</h1>
        <div>
          <div>
            <BasicButton onClick={() => setLevel("Basic")}>
              Basic
              <div>
                <span style={{ fontSize: "15px" }}>영어 1 ~ 3년차</span>
              </div>
            </BasicButton>

            <IntermediateButton onClick={() => setLevel("Intermediate")}>
              Intermediate
              <div>
                <span style={{ fontSize: "15px" }}>영어 4 ~ 6년차</span>
              </div>
            </IntermediateButton>
          </div>
          <div>
            <AdvancedButton onClick={() => setLevel("Advanced")}>
              Advanced
              <div>
                <span style={{ fontSize: "15px" }}>영어 7 ~ 9년차</span>
              </div>
            </AdvancedButton>
            <PrepButton onClick={() => setLevel("Prep")}>
              Prep
              <div>
                <span style={{ fontSize: "15px" }}>영어 10 ~ 12년차</span>
              </div>
            </PrepButton>
          </div>
        </div>
        <div style={{ textAlign: "center" }}>
          <h3>선택한 레벨 : {selectLevel}</h3>

          <TestPageLink to={`/test/${selectLevel}`} value={selectLevel}>
            시험 보러가기
          </TestPageLink>
        </div>
      </SelectLevelDiv>
    </Main>
  );
};
export default SelectLevelorigin;
